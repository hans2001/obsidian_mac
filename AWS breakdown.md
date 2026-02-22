(talk about the AWS and Cloudfare breakdown, **race condition** in AWS’s internal system that manages DNS for DynamoDB): 
(- tolerates “eventual consistency,” under normal circumstances )

worker: Planner and Enactor (multiple) 

flow:
An enactor got delayed, and the planner has a new DNS plan, and was applied by another enactor, and a cleanup job was triggered to remove old plans (the new enactor and the new plan) 

However, the staled enactor resumed and applied a old DNS plan, which overwrote the newer plan! (lost update, did no read the previous update)

after the overwrite, now DNS entry for dynamoDB is empty ,no one can reach it, so it cashes all the AWS services that depend on DynamoDB 

DNS Plan:
- **monitor the health and capacity** of DynamoDB’s backend load-balancers
- **mapping from endpoint → list of IP addresses** (or load-balancer addresses) that direct traffic to currently healthy & appropriate backends.