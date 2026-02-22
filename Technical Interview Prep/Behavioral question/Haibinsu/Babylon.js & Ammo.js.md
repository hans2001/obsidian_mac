**what is a static body , dynamic  body?** 
static body is an object that does not move! 
dynamic body simulated with the physics engine : respond to forces, collisions and gravity!

**what is mesh**
In 3D graphics, a mesh is the collection of vertices, edges, and faces that defines the shape of a 3D object. It represents the visible geometry that you see in a scene. In Babylon.js, meshes are the visual counterparts of objects in your scene, and you often pair them with physics bodies (from Ammo.js or similar libraries) to simulate real-world behavior.

 **what is restitution**
Restitution is a physical property that determines how much energy is conserved in a collision. Essentially, it defines the "bounciness" of an object. A high restitution value means the object bounces back with a significant amount of its original speed (almost like a superball), whereas a low restitution value means it will hardly bounce at all.

# Babylon
**engine** initialize the webGl context , **scene** contain all 3d objects 
render loop continuously updates the scene 
```javascript
// Initialize the Babylon engine and create a scene
const canvas = document.getElementById("renderCanvas");
const engine = new BABYLON.Engine(canvas, true);
const scene = new BABYLON.Scene(engine);

// Create a camera and position it
const camera = new BABYLON.ArcRotateCamera("Camera", Math.PI / 2, Math.PI / 4, 10, BABYLON.Vector3.Zero(), scene);
camera.attachControl(canvas, true);

// Add a simple light
const light = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(1, 1, 0), scene);

// Render loop
engine.runRenderLoop(() => {
    scene.render();
});
```

mesh - > visual object in scene, materials define appearance.
```javascript
// Create a basic box and assign a standard material
const box = BABYLON.MeshBuilder.CreateBox("box", { size: 1 }, scene);
const material = new BABYLON.StandardMaterial("boxMat", scene);
material.diffuseColor = new BABYLON.Color3(0.4, 0.6, 0.8);
box.material = material;
```

# Ammo.js
Rigid body dynamics
simulates physical objects with mass, friction and restitution! 
```javascript
// Create a dynamic rigid body for a falling box in Ammo.js
const mass = 1;
const boxShape = new Ammo.btBoxShape(new Ammo.btVector3(0.5, 0.5, 0.5));
const startTransform = new Ammo.btTransform();
startTransform.setIdentity();
startTransform.setOrigin(new Ammo.btVector3(0, 5, 0));

const localInertia = new Ammo.btVector3(0, 0, 0);
boxShape.calculateLocalInertia(mass, localInertia);

const motionState = new Ammo.btDefaultMotionState(startTransform);
const rbInfo = new Ammo.btRigidBodyConstructionInfo(mass, motionState, boxShape, localInertia);
const body = new Ammo.btRigidBody(rbInfo);

// Add the body to the physics world (assume physicsWorld is already set up)
physicsWorld.addRigidBody(body);
```

Constraints and joints
Constraints allow you to simulate joints and connections between bodies, such as hinges or sliders, which are crucial for simulating articulated structures.
```javascript
// Create a hinge constraint between two bodies (bodyA and bodyB)
const pivotA = new Ammo.btVector3(0, 0, 0);
const pivotB = new Ammo.btVector3(0, 0, 0);
const axisA = new Ammo.btVector3(0, 1, 0);
const axisB = new Ammo.btVector3(0, 1, 0);

const hingeConstraint = new Ammo.btHingeConstraint(bodyA, bodyB, pivotA, pivotB, axisA, axisB, true);
physicsWorld.addConstraint(hingeConstraint, true);
```

integration between the 2 
```javascript
// Assume 'mesh' is a Babylon.js mesh and 'body' is its corresponding Ammo.js body
function updatePhysics(deltaTime) {
    // Step the physics simulation
    physicsWorld.stepSimulation(deltaTime, 10);

    // Get the transform from Ammo.js and update the Babylon mesh
    const ms = body.getMotionState();
    if (ms) {
        let transform = new Ammo.btTransform();
        ms.getWorldTransform(transform);
        const origin = transform.getOrigin();
        const rotation = transform.getRotation();
        
        mesh.position.set(origin.x(), origin.y(), origin.z());
        mesh.rotationQuaternion = new BABYLON.Quaternion(rotation.x(), rotation.y(), rotation.z(), rotation.w());
    }
}
```