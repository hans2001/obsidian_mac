# Situation
“At HKUST I worked in a team of 3 to develop an Interactive educational platform. the professor wanted students to able to **play and learn**  topics in biology and chemistry. So we set out to build a SaaS with an immersive 3D classroom with real-time networking capability, and incorporate a RAG-based AI chat assistant that could resolve student's questions!.”
# Task
“As the team leader, I mainly worked on 3 tasks:
1. Main playground for the students where they can navigate to different modules of the course and interact with objects within,
2. Multi player ability so student can see and interact with each other
3. a **voice based Q&A loop** with our Chat Assistant that we could bring from 2D into VR.”
# Actions
**1) Interactive 3D + molecule control**  
“I browsed the internet to search for 3D asset, and i imported a ClassRoom like Scene , with **Babylon.js** as the game engine to render them. I then integrated a physics engine call **Ammo.js** to enable object collision and gravity.

For VR, I used the **WebXR Device API** and wrote the controller logic for **Meta Quest 3**. i enable user navigation by mounting the floor meshes, then i applied raycasting technique so user can point to objects in the space ; I wrote the logic for actions such as  **grabbing and rotating** a molecule.
I also modeled a simple **whiteboard** asset in Blender and brought it in, so we can show lecture notes or videos right inside the scene.”

**2) Real-time avatars (Colyseus on Fly.io)**  
i imported avatars assets from ReadPlayerme to display a user object, then i worked with **Colyseus** to enable create onJoinRoom and onLeaveRoom event so user can see each other. 
I then deployed this server on Fly.io and set up the configuration for load balancing and performed load tests to ensure availability

**3) ChatGPT in VR (Q&A + voice)**  
“We first shipped the **voice Q&A**  pipeline in our **2D web app**: we capture audio with **RecordRTC** API, then transcribe the audio to text with **OPENAI Whisper** 
Then we send the text to LLM powered Chat Assistant, after getting a response
we would first stream the output as text to our client, and user can choose to played it as an audio with **Microsoft Speech SDK** (voice selectable). And the next step is to integrate the pipeline to the VR scene, so user dont can directly interact the LLM while in the virtual space! 

# Results
we presented our platform to venture capitals and let students tried it, and the product secured **funding from HKUST’s Center for Education Innovation** and will be taken as additional learning tools for some of the UG level courses.