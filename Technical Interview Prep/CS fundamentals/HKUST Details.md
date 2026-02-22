WebXR endpoint control code: 
- **React shell**: a `<canvas>` ref + two `useEffect`s. One blocks the default page scroll on the mouse wheel; the other boots Babylon.
- **Babylon engine/scene**: creates an `Engine` and a `Scene`, sets a white background (`Color4(1,1,1,1)`), and renders each frame (`engine.runRenderLoop`), resizing on window changes.
- **Camera & lights**: an `ArcRotateCamera` for orbiting with mouse/touch; a hemispheric light (ambient-ish) and a point light.
- **Ground**: a big white ground mesh.
- **Model loading**: `SceneLoader.ImportMeshAsync("", "/", "1MBN.glb", scene)` loads a GLB named `1MBN.glb` from the web root. Everything that’s imported gets parented under a new `TransformNode` called `modelRoot`, which is lifted up (`y = 20`) so it floats above the ground. All child meshes are made pickable.
- **Highlighting**: a `HighlightLayer` is created to glow selected meshes.

```ts
const xrHelper = await scene.createDefaultXRExperienceAsync({
  floorMeshes: [ground],
});
```
- This spins up Babylon’s **WebXR experience helper**. Under the hood it uses the **WebXR Device API** provided by the browser/OS/VR runtime (e.g., Quest, SteamVR) to handle immersive sessions, input sources, teleports/raycasting, etc.
- Passing `floorMeshes` allows features like teleportation and correct floor alignment.

```ts
xrHelper.input.onControllerAddedObservable.add((controller) => {
  controller.onMotionControllerInitObservable.add((motionController) => {
    if (motionController.handness === "left" || motionController.handness === "right") {
      const xr_ids = motionController.getComponentIds();
      let triggerComponent = motionController.getComponent(xr_ids[0]);
      triggerComponent.onButtonStateChangedObservable.add(() => {
        ...
      });
    }
  });
});
```
- When a physical controller connects, Babylon fires `onControllerAddedObservable`.
- Once Babylon knows the specific **motion controller profile** (model + mapping), it fires `onMotionControllerInitObservable`. You then access its **components** (buttons, triggers, thumbsticks, squeeze, etc.).
- The code grabs the **first** component ID and treats it as the “trigger.” That often works (Quest trigger is commonly first), but it’s a bit brittle—more on improving this below.

**onButtonStateChangedObservable** fires on any change (pressed, touched, value).
```ts
if (triggerComponent.changes.pressed) {
  if (triggerComponent.pressed) {
    const pickedMesh = xrHelper.pointerSelection.getMeshUnderPointer
      ? xrHelper.pointerSelection.getMeshUnderPointer(controller.uniqueId)
      : scene.meshUnderPointer;

    if (pickedMesh && pickedMesh !== ground) {
      // "grab" the model by parenting it to the controller's root mesh
      modelRoot.setParent(motionController.rootMesh);

      // add glow for feedback
      modelRoot.getChildMeshes().forEach((mesh) => {
        highlightLayer.addMesh(mesh as Mesh, Color3.Yellow());
      });
    }
  } else {
    // "release"
    modelRoot.setParent(null);
    modelRoot.getChildMeshes().forEach((mesh) => {
      highlightLayer.removeMesh(mesh as Mesh);
    });
  }
}
```
- **Picking**: it uses Babylon’s XR pointer-selection feature if available (`xrHelper.pointerSelection.getMeshUnderPointer(controller.uniqueId)`) to see what the controller ray is pointing at; otherwise it falls back to the standard `scene.meshUnderPointer`.
- **Grab mechanic**: if the trigger is pressed while pointing at something (not the ground), it **parents** the entire `modelRoot`under the controller’s `rootMesh`. That makes the whole model move with your controller in VR—classic grab behavior.
- **Release mechanic**: when the trigger is released, it unparents the model and removes the highlight.

**Raycasting** is the trick of firing an invisible “ray” (a line with an origin and a direction) into the scene and asking: _what does this ray hit, and where?

### What’s a `TransformNode`?
- In Babylon, a **TransformNode** is an empty scene node: **no geometry**, just **position/rotation/scale** and a **pivot**.
- You use it as a **group handle**. Parent other meshes under it, then move/rotate/scale the whole group by moving the TransformNode.
### What your code does with it
- After importing the GLB, you make `modelRoot = new TransformNode("modelRoot")` and parent **all imported meshes** to it.
- Now the molecule acts like one object you can move as a unit.
### how the grab actually works (step-by-step)
1. **Raycast**: when you press the controller button, Babylon fires a ray from the controller and finds the mesh under the pointer.
2. **Check**: if you’re hitting something (and it’s not the ground), you decide to “grab.”
3. **Parenting to the controller**: you call  
    `modelRoot.setParent(motionController.rootMesh);`  
    This makes the **entire model group** follow the controller.
4. **Release**: when the button is released, you call  
    `modelRoot.setParent(null);`  
    That **detaches** the model from the controller so it stays where you left it.


