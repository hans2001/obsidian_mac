- [x] [[MongoDB optimizations]]
- [x] [[GraphQL pagination]]
- [x] [[Pothos]]
- [x] [[GraphQL Yoga]]
- [x] [[React window & Virtualization]]
- [x] [[Midas Infrastructure]]
- [x] [[Multi-Tenant Architecture]]

During my time at Midas, I architected the entire technical stack of a real-time media intelligence platform, and led 2 juniors to build it from prototyping all the way to a client-ready product. It was designed to process millions of new records in real time and transform raw news into structured intelligence for users.

On the frontend, we used React Window to perform virtualization, ElasticSearch for keyword matching, and built a library of generic UI components with Material UI and typescript. The backend was powered by Node.js and GraphQL, designed as a multi-tenant architecture for different business use case.

One of the biggest technical challenges was performance. To address this, I replaced Mongoose with the native Mongo driver, redesigned the data layer with optimized indexing and aggregation pipelines, and revised Protocol Buffers schema for efficiency. I also built a multi-layer caching system with GraphQL( schema based, resolver based). These enhancements significantly reduced latency and server load

To ensure seamless development and deployment, I built CI/CD pipelines that automate deployment and check test coverages, and managed multiple EC2 environments on AWS, including staging, UAT, and production. and spinned up reverse proxy servers for scalability

The product ultimately helped the company secure over 1 million funding and its first batch of clients from prestigious funds and retail investors!