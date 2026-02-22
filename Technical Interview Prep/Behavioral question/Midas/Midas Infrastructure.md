# **Nginx**
**Overview:**  
Nginx is a high-performance web server and reverse proxy that efficiently manages incoming web traffic. It’s renowned for its ability to handle high loads and for its low resource consumption.

**Key Uses in Your Deployment:**
- **Reverse Proxy & Load Balancing:** Nginx distributes incoming client requests to various application servers, ensuring even load distribution.
- **Static Content Serving:** It can directly serve static files, which reduces the load on backend servers.
- **SSL Termination:** By handling SSL/TLS handshakes, it offloads the encryption work from the application servers.

## What is a reverse proxy server? 
A **reverse proxy** is a server that sits between client devices and your backend servers. Instead of clients directly contacting your application servers, they interact with the reverse proxy, which then forwards the request to one or more backend servers and returns the response to the client. This setup can improve security, load distribution, and caching

**Example**
```nginx
http {
    # Define an upstream block for load balancing
    upstream backend_servers {
        server backend1.example.com;  # First backend server
        server backend2.example.com;  # Second backend server
    }

    server {
        listen 80;
        server_name www.example.com;

        location / {
            # Forward requests to the load-balanced backend group
            proxy_pass http://backend_servers;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
        }
    }
}
```

- **Forward Proxy:**  

    When you use a forward proxy, the proxy server acts on behalf of the client. For example, if a client wants to access a website anonymously, it sends requests to the proxy, which then fetches the content on behalf of the client. The client is hiding its identity from the destination server.
    
- **Reverse Proxy:**  

    A reverse proxy, on the other hand, sits in front of one or more backend servers. It receives requests from clients and forwards them to the appropriate server. In this case, the proxy is working on behalf of the server rather than the client. It "reverses" the roles by hiding the details of the backend servers from the client, which only interacts with the proxy.
    
# **PM2**

**Overview:**  
PM2 is a production process manager for Node.js applications. It allows you to keep your application alive forever, reload them without downtime, and facilitate common system admin tasks.

**Key Uses in Your Deployment:**

- **Process Management:** PM2 can automatically restart your application if it crashes, ensuring high availability.
- **Zero-Downtime Deployments:** It supports cluster mode and seamless reloads, minimizing downtime during updates.
- **Monitoring & Logging:** PM2 offers detailed metrics and log management, helping you diagnose issues in real time.

**Example:**  
Using PM2, you might start your application like this:

	`pm2 start app.js --name "myApp" --watch`

This command ensures that PM2 monitors your `app.js` file and restarts the process if any changes occur or if the process crashes.

# **Certbot**

**Overview:**  
Certbot is an open-source tool that automates the process of obtaining and renewing SSL/TLS certificates from Let’s Encrypt. It simplifies the process of securing your web server with HTTPS.

**Key Uses in Your Deployment:**

- **Automated Certificate Management:** Certbot automates the issuance and renewal of SSL certificates, reducing manual maintenance.
- **Improved Security:** By providing SSL certificates, it enables encrypted communication, which is crucial for secure user interactions and data transfers.

**Example:**  
You might run Certbot with a command like:

	`sudo certbot --nginx -d example.com -d www.example.com`

This command configures Nginx to use the new certificates and ensures that your domain is served over HTTPS.

# **CloudFront**

**Overview:**  
CloudFront is Amazon Web Services’ content delivery network (CDN) that securely delivers data, videos, applications, and APIs to customers globally with low latency.

**Key Uses in Your Deployment:**

- **Global Content Delivery:** CloudFront caches your content at edge locations worldwide, significantly improving access speed for global users.
- **Integration with S3:** In your case, CloudFront is used to serve images stored in an S3 bucket, ensuring fast and reliable content delivery.
- **Security Features:** It supports HTTPS, custom SSL certificates, and integrates with AWS WAF for additional security layers.