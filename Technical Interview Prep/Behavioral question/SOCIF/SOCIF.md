# EasyTransit — Mobile photo uploads (React Native + Azure + S3-compatible)

**Situation**  
“We needed riders to upload photos on iOS/Android, but uploads were slow, storage was expensive, and we had to stop bandwidth theft from hotlinking.”

**Task**  
“My goal was to ship a fast, reliable photo upload flow end-to-end—mobile app, server, storage—while keeping costs down and preventing hotlinking.”

**Action**
- “I rolled out the React Native uploader for iOS/Android and a TypeScript backend on **Azure Functions**.”
- “To speed it up, I added **client-side compression** (smaller files = faster network) and used **multipart uploads** so parts go in **parallel**, and any failed part retries without restarting the whole file.”
- “For storage, we integrated **Storj via its S3-compatible API** and stored **binary objects** instead of base64 to avoid the 33% bloat.”
- “To stop hotlinking, we served images only via **short-lived signed URLs**—links expire, so other sites can’t siphon our bandwidth.”
- “I also handled edge cases: unique keys to avoid overwrites, ETags for optimistic locking, and streaming paths when images get large.”
    
**Result**  
“We increased user-generated content by **~20%**, improved median response time by **~300ms**, and cut storage by **~45%**. The flow is stable, cheap, and secure.”

_(If they ask “how exactly did you get 300ms faster / 45% smaller?”: compression + multipart parallelism for speed; compressed WebP/JPEG + no base64 for size.)_

---
# MTR RollCall — Attendance automation (Electron + TypeScript + SQL Server)

**Situation**  
“MTR’s roll-call calculations were unreliable. They needed accurate daily status (Present/Late/Early Leave/Absent), correct reports, and exports for every school.”

**Task**  
“Rebuild the calculation engine and reporting so it’s deterministic, auditable, and easy to clone per school.”

**Action**
- “I built a **Windows desktop app** with **Electron + TypeScript**, pulling data from **SQL Server**.”
- “I rewrote the rules with a functional style (Lodash/fp): pure functions, clear pipelines for schedule → tap-events → daily status.”
- “I added report generation and export to **XLSX/PDF**, with templates ops can reuse across schools.”

**Result**  
“We delivered accurate, repeatable roll-call outputs and one-click reports. The app can be cloned to each school’s private system, so rollout is straightforward.”





## Multipart upload
upload single object to amazon s3 as parts (contiguous potion of the obj's data )
checksum of the object / size of the obj ( what is the checksum for )
retransmit the single part that is failing, without affecting other parts
after all parts are uploaded, Amazon s3 assembles them to create the object 
best practic:
to use multipart upload for obj that are > 100 MB!
## 3 step process

1. Initiate multipart upload -> amazon return a response with an upload ID -> 
the ID is used to upload parts, list parts, complete an upload or stop an upload! 

2. Checksum value is the same as in each part of the uploading object
Tag and part number must be recorded? 

3. If multipart upload was stopped during the process ,the upload ID must be abandoned as well! 

When upload is complete, amazon assembles the object using parts according to the part number(ascending order) 

## 1. What is Hotlinking?** 
why implemented the time_signed url?

**Hotlinking** is when someone directly embeds your image URL on their website, stealing your bandwidth.
```html
<!-- Your site hosts the image -->
https://yoursite.com/images/cat.jpg

<!-- Someone else's site uses YOUR image directly -->
<img src="https://yoursite.com/images/cat.jpg">
```
**Problem**: You pay for bandwidth when THEIR users view the image!
**Solution**: Signed URLs expire, preventing permanent hotlinking:

javascript
```javascript
// This URL only works for 15 minutes (default)
https://storj.io/bucket/image.jpg?X-Amz-Expires=900&X-Amz-Signature=abc123...

// After expiry, returns 403 Forbidden
```

## 2. What if URL Expires During Upload?
**Important clarification**: Signed URLs expire for **downloading**, not uploading!
javascript
```javascript
// Upload flow:
1. User uploads to YOUR server (no expiry)
2. YOUR server uploads to S3 (using permanent credentials)
3. Image stored permanently in S3

// Download flow:
1. User requests image
2. You generate signed URL (expires in 15 min)
3. User downloads using temporary URL
4. After 15 min, URL stops working (but image still exists)
```

If a signed URL expires while user is downloading:
- Download fails with 403 error
- Solution: Generate new signed URL and retry

**Interviewer**: "I see you're converting base64 to buffer. What's the memory implication?"
**Good Answer**: "Base64 encoding increases size by ~33%. For a 10MB image, we'd need 13.3MB in memory. For Azure Functions with 1.5GB memory limit, we could theoretically handle ~112 concurrent 10MB uploads, but practically less due to Node.js overhead. I'd implement streaming for large files."

## "What happens if two users upload to the same key simultaneously?"
**Good Answer**: "S3 has eventual consistency. The last write wins. To prevent this, I'd generate unique keys using UUID + timestamp, or implement optimistic locking with ETags."
## Improving response times by 300ms and reducing storage requirements by 45%.
how did i improve the response time and how did i reduced the storage requirements? 

- **Client-side compression**: Before sending the image, you compressed it (e.g., reducing resolution/quality). This cut down payload size, so the network transfer was faster.
- **Multipart uploads**: Parallelizing chunks instead of uploading the whole file sequentially reduced latency, especially for larger images.

- **Compression**: Images were stored in a compressed format (e.g., WebP or optimized JPEG) rather than raw/base64.
- **Avoiding base64 storage**: Base64 adds ~33% overhead. Storing actual binary buffers reduced storage footprint significantly.
- **Metadata separation**: Storing metadata (dimensions, type, etc.) in SQL instead of embedding it in the image file itself also saved space
