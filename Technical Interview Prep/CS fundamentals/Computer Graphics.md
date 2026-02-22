Main goal: description of a 3D scene into a 2D image that can be viewed.
  1. **transformations between spaces** 
  2. **conversions from one type of data to another**
# The graphics pipeline: 
### Main stages ( rasterization pipeline used by OpenGL ): 
**Input specification : what are the vertices :**
- the type of geometry we’ll be rendering is specified, in a form that is convenient for the rasterization process we’re using.

**Vertex shader: move to camera's perspective!** 
- world/object space → screen coordinates: we transform the coordinates of our input geometry from world space to a new space that is aligned with the camera’s perspective.

**Rasterization** (which pixels are insider triangle)
- Rasterization involves determining the pixels that a given primitive covers on the screen, so that we can compute the colors of each covered pixel.

**Fragment shader** what color is each pixel
for given pixel that is covered, compute the expected color. then we color each pixel with a specific color? fragment shader is used to perform per pixel computations, such as lighting calculation to determine whether a pixel is brightly lit or dim?

**Testing and blending**  which pixel are visible
perform depth test to find out whether a given pixel has already been computed for a closer obj? some geometry may be hidden by other geometry that’s in front of it resolve by a per pixel basis ,after the rasterization stage! 

输入顶点数据 (vertices) 
      ↓
[顶点着色器 Vertex Shader] → 坐标变换 (3D → 2D 摄像机视角)
      ↓
[装配/裁剪 Primitive Assembly & Clipping] → 组成三角形并裁掉视野外的部分
      ↓
[光栅化 Rasterization] → 把三角形转成屏幕上的像素候选 (fragments)
      ↓
[片段着色器 Fragment Shader] → 计算每个 fragment 的颜色 (光照/纹理等)
      ↓
[测试与混合 Depth & Blending] → 决定哪些 fragment 可见，输出到屏幕像素

fragment is candidate of pixel, pixel is what actually render on the screen
# Software Rasterizer: 
1. Read in triangles
    - Specify what geometry should be rendered. Initially, eventually we’ll want a way to import triangle meshes from files.
2. Convert triangels to windows coordinates
    - transform the 3D coordinates of each triangle vertex from world space to pixel coordinates, so that they can be rasterized.
3. Rasterize each triangle.
    - Rasterization determines **which pixels a given triangle covers in pixel space**.
    - We’ll use barycentric coordinates to test whether a given point is inside the triangle, and also to interpolate colors across the triangle (if different vertices have different colors)
4. Write interpolated color values per pixel
    - (using a z-buffer test to resolve depth comparisons)

#### The rasterizer works by **reducing continuous geometry (3D triangles) into discrete screen pixels, using interpolation and depth testing to decide each pixel’s color.**

## Barycentric Coordinates
The idea behind **Barycentric Coordinates** is that we’re going to use three numbers to describe the position of a point in relation to a specific triangle. These numbers represent more or less the “closeness” to each vertex - if a point is really close to vertex A of a triangle and far from B and C, we would say that the barycentric coordinates of the point are, say, 98% A, 1% B, and 1% C.

## Mesh
A **mesh** is generally just a set of polygons, with some associated data such as color, which we render.
In computer graphics we will often store meshes as what’s called an **indexed face-set**.

## indexed face-set
An indexed face-set consists of two arrays that store the contents of the mesh.

The first is simply a list of vertices. Each vertex is assigned an index, so the first vertex in the list is vertex 0, the next is vertex 1, etc.

The second array in the mesh representation is a list of indices. Each group of three indices in the index list determines a triangle in the mesh.

So, if the first three elements of the indices are `0`, `6`, and `7`, then the first triangle in the mesh consists of the vertices at index `0`, `6`, and `7`.

There’s one aspect about this that gets quite confusing - three is both the number of floats per a 3D vertex as well as the number of vertices in a triangle (and therefore the number of indices per triangle). Make sure you’re accounting for all of your threes!