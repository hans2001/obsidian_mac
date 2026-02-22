![[Screenshot 2025-02-21 at 4.52.28 PM.png]]
I was hired as an IT intern to assist the development of 2 projects. The 2 projects are both dashboard-like Software as a Services. 

The first project is a **Derivative valuation engine**, the goal was to automate the manual process of valuing derivatives, and the target user will be traders from the sales & trading team. Basically how it work is the platform will provide templates for users to put down product information, then these entries will be use as parameters for APIs that are developed by quantitative researchers, which computes the necessary pricing and valuation metrics. The platform then visualize those results in table formats, so that trader to filter and analyze the data efficiently
I worked on features such as visualizing data in tables, and parsing data from endpoints and refactoring codebase with advanced typescript techniques, such as writing generic typed UI components, turn naive tyeps to mapped types or Record types.

The second project is an **usage and expenditure monitoring dashboard** for the Bloomberg data license and B-pipe services. it was developed in response to the unreasonable billings from Bloomberg, and senior managements want to optimize budget and make each department accountable for the portion they used! I was tasked to build the dashboard from scratch, from the initial UI design, software architecture, to  the implement of both the frontend and backend. I worked with a team of 2 developers, and came up with a proof-of-concept implementation for a billing system that tracks Bloomberg service usage by department, which report the billing to each department at the end of the month!  and the software helped identified 15+ abnormal usage patterns

pattern such as:
- identified users with frequent idle sessions, and we sent them alerts
- plot the usage trend, do month-to-month comparison to detect abnormal increase
- repeated queries in short time span

## Results: 
developed protocols and regulations to guide the usage of bllomberg terminal in efficient manner