#include "stdafx.h"
#include "glad\glad.h"
#include "GLFW\glfw3.h"
#include "shader_s.h"
#include "camera.h"
#include "glm\glm.hpp"
#include "glm\gtc\matrix_transform.hpp"
#include "glm\gtc\type_ptr.hpp"
#include "stb_image.h"
#include "model.h"

#include <iostream>


void framebuffer_size_callback(GLFWwindow* window, int width, int height);
void mouse_callback(GLFWwindow* window, double xpos, double ypos);
void scroll_callback(GLFWwindow* window, double xoffset, double yoffset);
void processInput(GLFWwindow *window);
unsigned int loadTexture(const char *path);
unsigned int loadCubemap(vector<std::string> faces);

// settings
const unsigned int SCR_WIDTH = 800;
const unsigned int SCR_HEIGHT = 600;
const GLuint SHADOW_WIDTH = 2048, SHADOW_HEIGHT = 2048;
GLuint shadowMapFBO;
GLuint depthMapTexture;
glm::vec3 lightDir = glm::vec3(0.0f, 3.0f, 2.0f);
GLfloat lightAngle=0.0f;


// camera
Camera camera(glm::vec3(0.0f, 0.0f, 3.0f));
float lastX = SCR_WIDTH / 2.0f;
float lastY = SCR_HEIGHT / 2.0f;
bool firstMouse = true;

// timing
float deltaTime = 0.0f;
float lastFrame = 0.0f;

bool cameraAnimation = false;
bool wireframe = false;
bool flashlight = false;
float moveAnimation = 0.0f;
bool sword = false;
bool attack = false;
bool closetosword = false;
bool hammer = false;
bool closetohammer = false;
float attackTime = 0.0f;
float lastAttackTime = 0.0f;
bool dead = false;
bool selectHammer = false;
bool selectSword = false;
float attackAngle = 0.01f;
float fallAngle = 0.01f;
bool fall = false;
bool attackBack = false;
float snowY = 60.0f;
bool destroyCube = false;
float trans = 0.0f;
bool immune = false;
bool transBack = false;
bool activateSnow = false;
float pirrouete = 0.0f;
float finishPirrouette = false;




double random(int min, int max)
{
	return (double)rand() / (RAND_MAX + 1) * (min - max) + min;
}

glm::mat4 computeLightSpaceTrMatrix()
{
	const GLfloat near_plane = 1.0f, far_plane = 10.0f;
	glm::mat4 lightProjection = glm::ortho(-20.0f, 20.0f, -20.0f, 20.0f, near_plane, far_plane);

	glm::vec3 lightDirTr = glm::vec3(glm::rotate(glm::mat4(1.0f), glm::radians(lightAngle), glm::vec3(0.0f, 1.0f, 0.0f)) * glm::vec4(lightDir, 1.0f));
	glm::mat4 lightView = glm::lookAt(lightDirTr, glm::vec3(0.0f, 0.0f, 0.0f), glm::vec3(0.0f, 1.0f, 0.0f));

	return lightProjection * lightView;
}

void initFBOs()
{
	//generate FBO ID
	glGenFramebuffers(1, &shadowMapFBO);

	//create depth texture for FBO
	glGenTextures(1, &depthMapTexture);
	glBindTexture(GL_TEXTURE_2D, depthMapTexture);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT,
		SHADOW_WIDTH, SHADOW_HEIGHT, 0, GL_DEPTH_COMPONENT, GL_FLOAT, NULL);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

	//attach texture to FBO
	glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
	glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMapTexture, 0);
	glDrawBuffer(GL_NONE);
	glReadBuffer(GL_NONE);
	glBindFramebuffer(GL_FRAMEBUFFER, 0);
}

void initOpenGLState()
{
	glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
	glViewport(0, 0, SCR_WIDTH, SCR_HEIGHT);

	glEnable(GL_DEPTH_TEST); // enable depth-testing
	glDepthFunc(GL_LESS); // depth-testing interprets a smaller value as "closer"
						  //glEnable(GL_CULL_FACE); // cull face
	glCullFace(GL_BACK); // cull back face
	glFrontFace(GL_CCW); // GL_CCW for counter clock-wise
}


int main()
{
	// glfw: initialize and configure
	// ------------------------------
	glfwInit();
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

#ifdef __APPLE__
	glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE); // uncomment this statement to fix compilation on OS X
#endif

	// glfw window creation
	// --------------------
	GLFWwindow* window = glfwCreateWindow(SCR_WIDTH, SCR_HEIGHT, "GPS Project", NULL, NULL);
	if (window == NULL)
	{
		std::cout << "Failed to create GLFW window" << std::endl;
		glfwTerminate();
		return -1;
	}
	glfwMakeContextCurrent(window);
	glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);
	glfwSetCursorPosCallback(window, mouse_callback);
	glfwSetScrollCallback(window, scroll_callback);

	// tell GLFW to capture our mouse
	glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

	// glad: load all OpenGL function pointers
	// ---------------------------------------
	if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress))
	{
		std::cout << "Failed to initialize GLAD" << std::endl;
		return -1;
	}

	// configure global opengl state
	// -----------------------------
	glEnable(GL_DEPTH_TEST);
	initOpenGLState();
	initFBOs();
	// build and compile shaders
	// -------------------------
	Shader modelShader("shaders/model.vert", "shaders/model.frag");
	Shader lampShader("shaders/lamp.vert", "shaders/lamp.frag");
	Shader depthMapShader("shaders/depthMap.vert", "shaders/depthMap.frag");
	Shader skyboxShader("shaders/skybox.vert", "shaders/skybox.frag");

	// load models
	// -----------
	Model modelOBJ("objects/chogall/Chogall.obj");
	Model groundOBJ("objects/ground/ground.obj");
	Model towerOBJ("objects/Wooden-Watch-Tower/wooden watch tower2.obj");
	Model cottageOBJ("objects/cottage/cottage.obj");
	Model wallOBJ("objects/Wall/wall.obj");
	Model swordOBJ("objects/sword/NEDSTARK.obj");
	Model hammerOBJ("objects/hammer/thor.obj");
	Model dragonOBJ("objects/dragon/Deathwing.obj");
	Model windmillOBJ("objects/windmill2/windmill.obj");
	Model eliceOBJ("objects/windmill2/elice.obj");
	Model snowOBJ("objects/snow/Snowflake.obj");
	Model rainOBJ("objects/rain/rain.obj");
	Model houseOBJ("objects/house/farmhouse_obj.obj");
	Model announcer1OBJ("objects/announcer/ground.obj");
	Model announcer2OBJ("objects/announcer2/ground.obj");
	// set up vertex data (and buffer(s)) and configure vertex attributes
	// ------------------------------------------------------------------
	float vertices[] = {
		// positions          // normals           // texture coords
		-0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f,  0.0f,
		0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f,  0.0f,
		0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f,  1.0f,
		0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f,  1.0f,
		-0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f,  1.0f,
		-0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f,  0.0f,

		-0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f,  0.0f,
		0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f,  0.0f,
		0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f,  1.0f,
		0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  1.0f,  1.0f,
		-0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f,  1.0f,
		-0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,  0.0f,  0.0f,

		-0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  1.0f,  0.0f,
		-0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  1.0f,  1.0f,
		-0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
		-0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
		-0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  0.0f,  0.0f,
		-0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  1.0f,  0.0f,

		0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  1.0f,  0.0f,
		0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  1.0f,  1.0f,
		0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
		0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  0.0f,  1.0f,
		0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  0.0f,  0.0f,
		0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  1.0f,  0.0f,

		-0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f,  1.0f,
		0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  1.0f,  1.0f,
		0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f,  0.0f,
		0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f,  0.0f,
		-0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  0.0f,  0.0f,
		-0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f,  1.0f,

		-0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  0.0f,  1.0f,
		0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  1.0f,  1.0f,
		0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  1.0f,  0.0f,
		0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  1.0f,  0.0f,
		-0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  0.0f,  0.0f,
		-0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  0.0f,  1.0f
	};

	//SKYBOX
	float skyboxVertices[] = {
		// positions          
		-1.0f,  1.0f, -1.0f,
		-1.0f, -1.0f, -1.0f,
		1.0f, -1.0f, -1.0f,
		1.0f, -1.0f, -1.0f,
		1.0f,  1.0f, -1.0f,
		-1.0f,  1.0f, -1.0f,

		-1.0f, -1.0f,  1.0f,
		-1.0f, -1.0f, -1.0f,
		-1.0f,  1.0f, -1.0f,
		-1.0f,  1.0f, -1.0f,
		-1.0f,  1.0f,  1.0f,
		-1.0f, -1.0f,  1.0f,

		1.0f, -1.0f, -1.0f,
		1.0f, -1.0f,  1.0f,
		1.0f,  1.0f,  1.0f,
		1.0f,  1.0f,  1.0f,
		1.0f,  1.0f, -1.0f,
		1.0f, -1.0f, -1.0f,

		-1.0f, -1.0f,  1.0f,
		-1.0f,  1.0f,  1.0f,
		1.0f,  1.0f,  1.0f,
		1.0f,  1.0f,  1.0f,
		1.0f, -1.0f,  1.0f,
		-1.0f, -1.0f,  1.0f,

		-1.0f,  1.0f, -1.0f,
		1.0f,  1.0f, -1.0f,
		1.0f,  1.0f,  1.0f,
		1.0f,  1.0f,  1.0f,
		-1.0f,  1.0f,  1.0f,
		-1.0f,  1.0f, -1.0f,

		-1.0f, -1.0f, -1.0f,
		-1.0f, -1.0f,  1.0f,
		1.0f, -1.0f, -1.0f,
		1.0f, -1.0f, -1.0f,
		-1.0f, -1.0f,  1.0f,
		1.0f, -1.0f,  1.0f
	};

	vector<std::string> faces
	{
		"textures/skybox/right.tga",
		"textures/skybox/left.tga",
		"textures/skybox/top.tga",
		"textures/skybox/bottom.tga",
		"textures/skybox/back.tga",
		"textures/skybox/front.tga"
	};
	unsigned int cubemapTexture = loadCubemap(faces);

	skyboxShader.use();
	skyboxShader.setInt("skybox", 0);
	
	// skybox VAO
	unsigned int skyboxVAO, skyboxVBO;
	glGenVertexArrays(1, &skyboxVAO);
	glGenBuffers(1, &skyboxVBO);
	glBindVertexArray(skyboxVAO);
	glBindBuffer(GL_ARRAY_BUFFER, skyboxVBO);
	glBufferData(GL_ARRAY_BUFFER, sizeof(skyboxVertices), &skyboxVertices, GL_STATIC_DRAW);
	glEnableVertexAttribArray(0);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
	//END SKYBOX

	glm::vec3 cubePositions[] = {
		glm::vec3(2.0f,  -0.75f,  0.0f)
	};
	glm::vec3 lastPos;
	lastPos.x = cubePositions[0].x;
	lastPos.y = cubePositions[0].y;
	lastPos.z = cubePositions[0].z;

	glm::vec3 pointLightPositions[] = {
		glm::vec3(1.2f, 1.5f, 2.0f),
		glm::vec3(5.0f, 8.5f, -5.0f),
		glm::vec3(-6.75f, -0.25f, 6.75f),
		glm::vec3(6.0f, 2.5f, 6.0f)
	};


	// first, configure the cube's VAO (and VBO)
	unsigned int VBO, cubeVAO;
	glGenVertexArrays(1, &cubeVAO);
	glGenBuffers(1, &VBO);

	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);

	glBindVertexArray(cubeVAO);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)0);
	glEnableVertexAttribArray(0);
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(3 * sizeof(float)));
	glEnableVertexAttribArray(1);
	glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(6 * sizeof(float)));
	glEnableVertexAttribArray(2);

	// second, configure the light's VAO (VBO stays the same; the vertices are the same for the light object which is also a 3D cube)
	unsigned int lightVAO;
	glGenVertexArrays(1, &lightVAO);
	glBindVertexArray(lightVAO);

	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	// note that we update the lamp's position attribute's stride to reflect the updated buffer data
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)0);
	glEnableVertexAttribArray(0);



	// load textures (we now use a utility function to keep the code more organized)
	// -----------------------------------------------------------------------------
	unsigned int diffuseMap = loadTexture("textures/container2.png");
	unsigned int specularMap = loadTexture("textures/container2_specular.png");

	glm::vec3 swordPos;
	swordPos.x = 3.75f;
	swordPos.y = 7.90f;
	swordPos.z = -6.3f;
	float swordAngle = 75.0f;
	float swordAngle2 = 0.0f;
	float weaponPosAngle = 0.0f;
	glm::vec3 swordV1 = glm::vec3(1.0f, 0.0f, 0.0f);
	glm::vec3 swordV2 = glm::vec3(0.0f, 0.0f, 1.0f);
	glm::vec3 swordScale = glm::vec3(0.07f, 0.07f, 0.07f);


	glm::vec3 hammerPos;
	hammerPos.x = -7.0f;
	hammerPos.y = -1.40f;
	hammerPos.z = -7.0f;
	hammerPos.x = lastPos.x+2.0f;
	hammerPos.y = lastPos.y-0.75f;
	hammerPos.z = lastPos.z;
	float hammerAngle = 180.0f;
	float hammerAngle2 = 0.0f;
	float hammerPosAngle = 0.0f;
	glm::vec3 hammerV1 = glm::vec3(0.0f, 0.0f, 1.0f);
	glm::vec3 hammerV2 = glm::vec3(0.0f, 0.0f, 1.0f);

	glm::vec3 snow[300];
	for (int i = 0; i < 300; i++)
	{ 
		snow[i] = glm::vec3(10.0f+random(0, 2000) / 100, snowY+ random(0, 600)/10, 7.5f+random(0, 2000) / 100);
	}

	glm::vec3 announcer1Pos = glm::vec3(100.0f, 100.0f, 100.0f);
	glm::vec3 announcer2Pos = glm::vec3(100.0f, 100.0f, 100.0f);
	// render loop
	// -----------
	while (!glfwWindowShouldClose(window))
	{
		// per-frame time logic
		// --------------------
		float currentFrame = glfwGetTime();
		deltaTime = currentFrame - lastFrame;
		lastFrame = currentFrame;

		// input
		// -----
		processInput(window);

		// render
		// ------
		glClearColor(0.05f, 0.05f, 0.05f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


		//IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII
		//render the scene to the depth buffer (first pass)
		depthMapShader.use();
		depthMapShader.setMat4("lightSpaceTrMatrix", computeLightSpaceTrMatrix());

		glViewport(0, 0, SHADOW_WIDTH, SHADOW_HEIGHT);
		glBindFramebuffer(GL_FRAMEBUFFER, shadowMapFBO);
		glClear(GL_DEPTH_BUFFER_BIT);

		//create model matrix for nanosuit
		//send model matrix to shader
		glm::mat4 model;
		model = glm::mat4();
		model = glm::translate(model, glm::vec3(0.0f, -2.0f, 0.0f));
		model = glm::scale(model, glm::vec3(1.2f, 1.2f, 1.2f));
		modelShader.setMat4("model", model);
		modelOBJ.Draw(depthMapShader);

		//create model matrix for ground
		model = glm::mat4();
		model = glm::translate(model, glm::vec3(0.0f, -2.0f, 0.0f));
		//send model matrix to shader
		depthMapShader.setMat4("model", model);
		groundOBJ.Draw(depthMapShader);

		glBindFramebuffer(GL_FRAMEBUFFER, 0);


		//IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII
		//render the scene (second pass)
		modelShader.use();
		modelShader.setMat4("lightSpaceTrMatrix", computeLightSpaceTrMatrix());

		// camera/view transformation
		glm::mat4 view;
		float radius = 15.0f;
		float camX = sin(glfwGetTime()) * radius;
		float camZ = cos(glfwGetTime()) * radius;
		if (cameraAnimation == 1)
			view = glm::lookAt(glm::vec3(camX, 4.0f, camZ), glm::vec3(0.0f, 0.0f, 0.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		else
			view = camera.GetViewMatrix();

		modelShader.setMat4("view", view);

		glViewport(0, 0, SCR_WIDTH, SCR_HEIGHT);

		modelShader.use();
		//bind the depth map
		glActiveTexture(GL_TEXTURE3);
		glBindTexture(GL_TEXTURE_2D, depthMapTexture);
		modelShader.setInt("shadowMap", 3);
		modelShader.setInt("material.diffuse", 0);
		modelShader.setInt("material.specular", 1);

		
		glm::mat4 projection = glm::perspective(glm::radians(camera.Zoom), (float)SCR_WIDTH / (float)SCR_HEIGHT, 0.1f, 100.0f);
		
		// don't forget to enable shader before setting uniforms
		// be sure to activate shader when setting uniforms/drawing objects
		modelShader.use();
		modelShader.setFloat("trans", 1.0f);
		modelShader.setMat4("projection", projection);
		
		modelShader.setVec3("viewPos", camera.Position);
		modelShader.setFloat("shininess", 32.0f);




		if(flashlight==true)
			modelShader.setBool("flashlight", true);
		else
			modelShader.setBool("flashlight", false);

		// directional light
		modelShader.setVec3("dirLight.direction", lightDir);
		modelShader.setVec3("dirLight.ambient", 0.05f, 0.05f, 0.05f);
		modelShader.setVec3("dirLight.diffuse", 0.6f, 0.6f, 0.6f);
		modelShader.setVec3("dirLight.specular", 0.5f, 0.5f, 0.5f);

		// point light 1
		for (int i = 0; i < 4; i++)
		{
			modelShader.setVec3("pointLights[" + std::to_string(i) + "].position", pointLightPositions[i]);
			modelShader.setVec3("pointLights[" + std::to_string(i) + "].ambient", 0.05f, 0.05f, 0.05f);
			modelShader.setVec3("pointLights[" + std::to_string(i) + "].diffuse", 0.8f, 0.8f, 0.8f);
			modelShader.setVec3("pointLights[" + std::to_string(i) + "].specular", 1.0f, 1.0f, 1.0f);
			modelShader.setFloat("pointLights[" + std::to_string(i) + "].constant", 1.0f);
			modelShader.setFloat("pointLights[" + std::to_string(i) + "].linear", 0.09);
			modelShader.setFloat("pointLights[" + std::to_string(i) + "].quadratic", 0.032);
		}

		// spot light
		modelShader.setVec3("spotLight.position", camera.Position);
		modelShader.setVec3("spotLight.direction", camera.Front);
		modelShader.setVec3("spotLight.ambient", 0.0f, 0.0f, 0.0f);
		modelShader.setVec3("spotLight.diffuse", 1.0f, 1.0f, 1.0f);
		modelShader.setVec3("spotLight.specular", 1.0f, 1.0f, 1.0f);
		modelShader.setFloat("spotLight.constant", 1.0f);
		modelShader.setFloat("spotLight.linear", 0.09);
		modelShader.setFloat("spotLight.quadratic", 0.032);
		modelShader.setFloat("spotLight.cutOff", glm::cos(glm::radians(12.5f)));
		modelShader.setFloat("spotLight.outerCutOff", glm::cos(glm::radians(15.0f)));
		// view/projection transformations
		


		//CHARACTER
		//-----------------------------------------------------------------
		if (wireframe == true)
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		else
			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		model = glm::mat4();
		model = glm::translate(model, glm::vec3(0.0f, -2.0f, 0.0f));
		if (attack == true && (selectHammer == true || (hammer==true && sword==false)) && abs(camera.Position.x - model[0][0]) < 1.5f && abs(camera.Position.y - model[1][0]) < 2.0f && abs(camera.Position.z - model[2][0]) < 2.0f)
		{
			dead = true;
		}
		if (attack == true && (selectSword == true || (sword==true && hammer==false)) && abs(camera.Position.x - model[0][0]) < 2.5f && abs(camera.Position.y - model[1][0]) < 2.0f && abs(camera.Position.z - model[2][0]) < 2.5f)
		{
			immune = true;
		}

		if (dead == true)
		{
			if(fall==false)
				fallAngle = fallAngle + 0.03f;
			if (fallAngle > 1.4f)
				fall = true;
			model = glm::rotate(model, -fallAngle, glm::vec3(1.0f, 0.0f, 0.0f));
		}

		model = glm::scale(model, glm::vec3(1.2f, 1.2f, 1.2f));
		modelShader.setMat4("model", model);
		modelOBJ.Draw(modelShader);

		

		//GROUND
		//-----------------------------------------------------------------
		model = glm::mat4();
		model = glm::translate(model, glm::vec3(0.0f, -2.0f, 0.0f));
		modelShader.setMat4("model", model);
		groundOBJ.Draw(modelShader);

		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		//DRAGON
		//-----------------------------------------------------------------
		model = glm::mat4();
		model = glm::translate(model, glm::vec3(0.0f, 70.0f, 0.0f));
		model = glm::rotate(model, -(float)glfwGetTime(), glm::vec3(0.0f, 1.0f, 0.0f));
		model = glm::translate(model, glm::vec3(50.0f, 0.0f, 0.0f));
		int t = glfwGetTime();
		if (t % 10 == 0)
		{
			pirrouete = pirrouete + 1.5f;
			if (pirrouete > 359.0f)
				finishPirrouette = true;
			if (finishPirrouette == true)
			{
				pirrouete = 0.0f;
				finishPirrouette = false;
			}
			model = glm::rotate(model, glm::radians(pirrouete), glm::vec3(0.0f, 0.0f, 1.0f));
		}
		modelShader.setMat4("model", model);
		dragonOBJ.Draw(modelShader);

		//TOWER
		//-----------------------------------------------------------------
		model = glm::mat4();
		model = glm::translate(model, glm::vec3(5.0f, -3.0f, -5.0f));
		model = glm::scale(model, glm::vec3(1.5f, 1.5f, 1.5f));
		model = glm::rotate(model, glm::radians(180.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		modelShader.setMat4("model", model);
		towerOBJ.Draw(modelShader);

		//COTTAGE
		//-----------------------------------------------------------------
		/*
		model = glm::mat4();
		model = glm::translate(model, glm::vec3(-6.0f, -1.8f, -3.0f));
		model = glm::scale(model, glm::vec3(0.66f, 0.66f, 0.66f));
		model = glm::rotate(model, glm::radians(180.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		modelShader.setMat4("model", model);
		cottageOBJ.Draw(modelShader);*/


		model = glm::mat4();
		model = glm::translate(model, glm::vec3(-6.0f, -1.8f, -3.0f));
		model = glm::rotate(model, glm::radians(180.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		model = glm::scale(model, glm::vec3(0.2f, 0.2f, 0.2f));
		modelShader.setMat4("model", model);
		houseOBJ.Draw(modelShader);


		//WINDMILL
		//-----------------------------------------------------------------
		model = glm::mat4();
		model = glm::translate(model, glm::vec3(-6.8f, -2.0f, 6.8f));
		model = glm::rotate(model, glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		model = glm::scale(model, glm::vec3(1.0f, 1.0f, 1.0f));
		modelShader.setMat4("model", model);
		windmillOBJ.Draw(modelShader);

		model = glm::mat4();
		model = glm::translate(model, glm::vec3(-6.7f, -2.0f, 6.8f));
		model = glm::translate(model, glm::vec3(0, 5.1f, 0));
		model = glm::rotate(model, (float)glfwGetTime(), glm::vec3(1.0f, 0.0f, 0.0f));
		model = glm::translate(model, glm::vec3(0, -5.1f, 0));
		model = glm::rotate(model, glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		model = glm::scale(model, glm::vec3(1.0f, 1.0f, 1.0f));
		modelShader.setMat4("model", model);
		eliceOBJ.Draw(modelShader);


		//snow
		//-----------------------------------------------------------------
		if(activateSnow==true)
		{
		for (int i = 0; i < 300; i++)
		{
			snow[i].y = snow[i].y - 0.5f;

			model = glm::mat4();
			if (snow[i].y < -1.75f)
				snow[i].y = snow[i].y + 60.0f;
			model = glm::translate(model, snow[i]);
			model = glm::rotate(model, glm::radians(90.0f), glm::vec3(1.0f, 0.0f, 0.0f));
			model = glm::rotate(model, (float) glfwGetTime(), glm::vec3(0.0f, 1.0f, 0.0f));
			model = glm::scale(model, glm::vec3(0.05f, 0.05f, 0.05f));
			modelShader.setMat4("model", model);
			snowOBJ.Draw(modelShader);
		}
		}


		//HAMMER
		//-----------------------------------------------------------------
		if (abs(camera.Position.x - hammerPos.x) < 1.0f && abs(camera.Position.y - hammerPos.y) < 1.0f && abs(camera.Position.z - hammerPos.z) < 1.0f)
			closetohammer = true;
		if ((hammer == true && sword==false) || selectHammer == true)
		{
			hammerPos.x = 0.2f;
			hammerPos.y = 0.0f;
			hammerPos.z = -1.0f;
			if (camera.Yaw == -45.0f || camera.Yaw == 135.0f)
				weaponPosAngle = camera.Yaw;
			else
				weaponPosAngle = -90 - camera.Yaw;
			hammerAngle = 90.0f;
			hammerAngle2 = 30.0f;
			hammerV1 = glm::vec3(0.0f, 1.0f, 0.0f);
			hammerV2 = glm::vec3(1.0f, 0.0f, 0.0f);
		}

		if (selectSword == true)
		{
			hammerPos.x = 100.0f;
			hammerPos.y = 100.0f;
			hammerPos.z = 100.0f;
		}

		model = glm::mat4();
		if (hammer == true)
		{
			model = glm::translate(model, glm::vec3(camera.Position.x, camera.Position.y, camera.Position.z));
			model = glm::rotate(model, glm::radians(weaponPosAngle), glm::vec3(0.0f, 1.0f, 0.0f));
		}

		model = glm::translate(model, glm::vec3(hammerPos.x, hammerPos.y, hammerPos.z));

		if (attack == true && ((hammer == true && sword == false) || selectHammer == true))
		{
			attackAngle = attackAngle + 0.05f;
			if (attackAngle > 0.90f)
				attackBack = true;
			if (attackBack == true)
				attackAngle = attackAngle - 0.10f;
			if (attackAngle < 0.0f)
			{ 
				attackBack = false;
				attack = false;
			}
			model = glm::translate(model, glm::vec3(-0.1f, -0.3f, 0.2f));
			model = glm::rotate(model, -attackAngle, glm::vec3(1.0f, 0.0f, 0.0f));
			model = glm::translate(model, glm::vec3(0.1f, 0.3f, -0.2f));
		}
		
		model = glm::scale(model, glm::vec3(0.02f, 0.02f, 0.02f));
		model = glm::rotate(model, glm::radians(hammerAngle), hammerV1);
		model = glm::rotate(model, glm::radians(hammerAngle2), hammerV2);

		modelShader.setMat4("model", model);
		hammerOBJ.Draw(modelShader);


		//SWORD
		//-----------------------------------------------------------------
		if (abs(camera.Position.x - swordPos.x) < 0.5 && abs(camera.Position.y - swordPos.y) < 0.5 && abs(camera.Position.z - swordPos.z) < 1.0)
			closetosword = true;

		if ((sword == true && hammer==false) || selectSword==true)
		{
			swordPos.x = 0.4f;
			swordPos.y = 0.2f;
			swordPos.z = - 1.0f;
			if (camera.Yaw == -45.0f || camera.Yaw == 135.0f)
				weaponPosAngle = camera.Yaw;
			else
				weaponPosAngle = -90-camera.Yaw;
			swordAngle = 270.0f;
			swordAngle2 = 90.0f;
			swordScale = glm::vec3(0.04f, 0.04f, 0.04f);
		}
		if (selectHammer == true)
		{
			swordPos.x = 100.0f;
			swordPos.y = 100.0f;
			swordPos.z = 100.0f;
		}
		
		model = glm::mat4();
		if (sword == true)
		{
			model = glm::translate(model, glm::vec3(camera.Position.x, camera.Position.y, camera.Position.z));
			model = glm::rotate(model, glm::radians(weaponPosAngle), glm::vec3(0.0f, 1.0f, 0.0f));
		}

		model = glm::translate(model, glm::vec3(swordPos.x, swordPos.y, swordPos.z));

		if (attack == true && ((sword==true && hammer==false) || selectSword==true))
		{
			attackAngle = attackAngle + 0.05f;
			if (attackAngle > 1.30f)
				attackBack = true;
			if (attackBack == true)
				attackAngle = attackAngle - 0.10f;
			if (attackAngle < 0.0f)
			{
				attackBack = false;
				attack = false;
			}
			model = glm::translate(model, glm::vec3(0.0f, -0.8f, 0.0f));
			model = glm::rotate(model, -attackAngle, glm::vec3(1.0f, 0.0f, 0.0f));
			model = glm::translate(model, glm::vec3(0.0f, 0.8f, 0.0f));
			/*
			float attackAngle = (float)glfwGetTime();
			model = glm::translate(model, glm::vec3(0.0f, -0.5f, 0.5f));
			model = glm::rotate(model, -attackAngle * 5, glm::vec3(1.0f, 0.0f, 0.0f));
			model = glm::translate(model, glm::vec3(0.0f, 0.5f, -0.5f));
			if (attackAngle>lastAttackTime+1.3f)
				attack = false;*/
		}
		model = glm::rotate(model, glm::radians(swordAngle), swordV1);
		model = glm::rotate(model, glm::radians(swordAngle2), swordV2);
		
		model = glm::scale(model, swordScale);
		modelShader.setMat4("model", model);
		swordOBJ.Draw(modelShader);


		model = glm::mat4();
		model = glm::translate(model, glm::vec3(-4.7, -2.2f, -10.0f));
		model = glm::scale(model, glm::vec3(0.8f, 0.8f, 0.8f));
		modelShader.setMat4("model", model);
		wallOBJ.Draw(modelShader);

		
		//ANNOUNCER1
		//-----------------------------------------------------------------
		if (immune == true && !(dead==true && selectSword==true))
		{
			announcer1Pos.x = -4.07f;
			announcer1Pos.y = -0.5f;
			announcer1Pos.z = -2.5f;
			trans = trans + 0.003;
			if (trans >= 0.95)
				transBack = true;
			if (transBack == true)
			{
				announcer1Pos.x = 100.0f;
				announcer1Pos.y = 100.0f;
				announcer1Pos.z = 100.0f;
				trans = 0.0f;
				transBack = false;
				immune = false;
			}
		}
		model = glm::mat4();
		model = glm::translate(model, announcer1Pos);
		model = glm::scale(model, glm::vec3(0.1f, 0.1f, 0.1f));
		model = glm::rotate(model, glm::radians(90.0f), glm::vec3(1.0f, 0.0f, 0.0f));
		model = glm::rotate(model, glm::radians(-90.0f), glm::vec3(0.0f, 0.0f, 1.0f));
		modelShader.setMat4("model", model);
		announcer1OBJ.Draw(modelShader);

		//ANNOUNCER2
		//-----------------------------------------------------------------
		if (dead == true)
		{
			announcer2Pos.x = -4.07f;
			announcer2Pos.y = -0.5f;
			announcer2Pos.z = -2.5f;
		}
		model = glm::mat4();
		model = glm::translate(model, announcer2Pos);
		model = glm::scale(model, glm::vec3(0.1f, 0.1f, 0.1f));
		model = glm::rotate(model, glm::radians(90.0f), glm::vec3(1.0f, 0.0f, 0.0f));
		model = glm::rotate(model, glm::radians(-90.0f), glm::vec3(0.0f, 0.0f, 1.0f));
		modelShader.setMat4("model", model);
		announcer2OBJ.Draw(modelShader);

		
		model = glm::mat4();
		model = glm::rotate(model, glm::radians(-90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		model = glm::translate(model, glm::vec3(4.7, -2.2f, -10.0f));
		model = glm::scale(model, glm::vec3(0.8f, 0.8f, 0.8f));
		modelShader.setMat4("model", model);
		wallOBJ.Draw(modelShader);

		model = glm::mat4();
		model = glm::rotate(model, glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		model = glm::translate(model, glm::vec3(-4.7, -2.2f, -10.0f));
		model = glm::scale(model, glm::vec3(0.8f, 0.8f, 0.8f));
		modelShader.setMat4("model", model);
		wallOBJ.Draw(modelShader);

		model = glm::mat4();
		model = glm::translate(model, glm::vec3(4.7, -2.2f, -10.0f));
		model = glm::scale(model, glm::vec3(0.8f, 0.8f, 0.8f));
		modelShader.setMat4("model", model);
		wallOBJ.Draw(modelShader);

		model = glm::mat4();
		model = glm::translate(model, glm::vec3(-4.7, -2.2f, 10.0f));
		model = glm::scale(model, glm::vec3(0.8f, 0.8f, 0.8f));
		modelShader.setMat4("model", model);
		wallOBJ.Draw(modelShader);

		model = glm::mat4();
		model = glm::translate(model, glm::vec3(4.7, -2.2f, 10.0f));
		model = glm::scale(model, glm::vec3(0.8f, 0.8f, 0.8f));
		modelShader.setMat4("model", model);
		wallOBJ.Draw(modelShader);

		model = glm::mat4();
		model = glm::rotate(model, glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		model = glm::translate(model, glm::vec3(4.7, -2.2f, 10.0f));
		model = glm::scale(model, glm::vec3(0.8f, 0.8f, 0.8f));
		modelShader.setMat4("model", model);
		wallOBJ.Draw(modelShader);

		model = glm::mat4();
		model = glm::rotate(model, glm::radians(-90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		model = glm::translate(model, glm::vec3(-4.7, -2.2f, 10.0f));
		model = glm::scale(model, glm::vec3(0.8f, 0.8f, 0.8f));
		modelShader.setMat4("model", model);
		wallOBJ.Draw(modelShader);


		//CUBE
		glm::mat4 cube;
		cube = glm::translate(cube, cubePositions[0]);
		// bind diffuse map
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, diffuseMap);
		// bind specular map
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, specularMap);

		// render containers
		glBindVertexArray(cubeVAO);
		// calculate the model matrix for each object and pass it to shader before drawing

		if (attack == true && sword == true && ((lastPos.x - camera.Position.x<0.5f && lastPos.x - camera.Position.x>-1.5f && abs(camera.Position.y - lastPos.y) < 1.5 && abs(camera.Position.z - lastPos.z) < 1.5) || (camera.Position.x - lastPos.x > 3.5 && camera.Position.x - lastPos.x < 4.5 && abs(camera.Position.y - lastPos.y) < 1.5 && abs(camera.Position.z - lastPos.z) < 1.5) || (lastPos.x - camera.Position.x > -3.5f && lastPos.x - camera.Position.x < -1.5f && abs(camera.Position.y - lastPos.y) < 1.5 && camera.Position.z - lastPos.z<-1.5f && camera.Position.z - lastPos.z>-2.5f) || (lastPos.x - camera.Position.x > -3.5f && lastPos.x - camera.Position.x < -1.5f && abs(camera.Position.y - lastPos.y) < 1.5 && camera.Position.z - lastPos.z<2.5f && camera.Position.z - lastPos.z>1.5f)))
		{
			destroyCube = true;
		}

		if (lastPos.x - camera.Position.x<0.0f && lastPos.x - camera.Position.x>-1.0f && abs(camera.Position.y - lastPos.y) < 1 && abs(camera.Position.z - lastPos.z) < 1 && lastPos.x < 6.75)
		{
			lastPos.x = lastPos.x + moveAnimation;
			hammerPos.x = hammerPos.x + moveAnimation;
		}
		if (camera.Position.x - lastPos.x>3 && camera.Position.x - lastPos.x<4 && abs(camera.Position.y - lastPos.y)<1 && abs(camera.Position.z - lastPos.z)<1 && lastPos.x>-10.75)
		{
			lastPos.x = lastPos.x - moveAnimation;
			hammerPos.x = hammerPos.x - moveAnimation;
		}
		if (lastPos.x - camera.Position.x > -3.0f && lastPos.x - camera.Position.x < -1.0f && abs(camera.Position.y - lastPos.y) < 1 && camera.Position.z - lastPos.z<-1.0f && camera.Position.z - lastPos.z>-2.0f && lastPos.z < 8.75)
		{
			lastPos.z = lastPos.z + moveAnimation;
			hammerPos.z = hammerPos.z + moveAnimation;
		}
		if (lastPos.x - camera.Position.x>-3.0f && lastPos.x - camera.Position.x<-1.0f && abs(camera.Position.y - lastPos.y)<1 && camera.Position.z - lastPos.z<2.0f && camera.Position.z - lastPos.z>1.0f && lastPos.z>-8.75)
		{ 
			lastPos.z = lastPos.z - moveAnimation;
			hammerPos.z = hammerPos.z - moveAnimation;
		}

		if (destroyCube == true && ((sword == true && hammer == false) || selectSword == true) && attackBack==true)
		{
			lastPos.x = 150.0f;
			lastPos.y = 150.0f;
			lastPos.z = 150.0f;
		}

		cube = glm::translate(cube, glm::vec3(lastPos.x, lastPos.y, lastPos.z));
		
		modelShader.setMat4("model", cube);
		glDrawArrays(GL_TRIANGLES, 0, 36);

		// also draw the lamp object(s)
		lampShader.use();
		lampShader.setMat4("projection", projection);
		lampShader.setMat4("view", view);

		// we now draw as many light bulbs as we have point lights.
		glBindVertexArray(lightVAO);
		for (unsigned int i = 0; i < 4; i++)
		{
			model = glm::mat4();
			model = glm::translate(model, pointLightPositions[i]);
			model = glm::scale(model, glm::vec3(0.2f)); // Make it a smaller cube
			lampShader.setMat4("model", model);
			glDrawArrays(GL_TRIANGLES, 0, 36);
		}
		//for lightb obj
		//modelOBJ.Draw(lampShader);

		model = glm::mat4();
		model = glm::rotate(model, glm::radians(lightAngle), glm::vec3(0.0f, 1.0f, 0.0f));
		model = glm::translate(model, lightDir);
		model = glm::scale(model, glm::vec3(0.2f)); // Make it a smaller cube
		lampShader.setMat4("model", model);
		//glDrawArrays(GL_TRIANGLES, 0, 36);

		// draw skybox as last
		glDepthFunc(GL_LEQUAL);  // change depth function so depth test passes when values are equal to depth buffer's content
		skyboxShader.use();
		view = glm::mat4(glm::mat3(camera.GetViewMatrix())); // remove translation from the view matrix
		skyboxShader.setMat4("view", view);
		skyboxShader.setMat4("projection", projection);
		// skybox cube

		glBindVertexArray(skyboxVAO);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_CUBE_MAP, cubemapTexture);
		glDrawArrays(GL_TRIANGLES, 0, 36);


		glBindVertexArray(0);
		glDepthFunc(GL_LESS); // set depth function back to default

		
		// glfw: swap buffers and poll IO events (keys pressed/released, mouse moved etc.)
		// -------------------------------------------------------------------------------
		glfwSwapBuffers(window);
		glfwPollEvents();
	}
	glDeleteVertexArrays(1, &cubeVAO);
	glDeleteVertexArrays(1, &skyboxVAO);
	glDeleteBuffers(1, &skyboxVAO);
	glDeleteBuffers(1, &VBO);

	// glfw: terminate, clearing all previously allocated GLFW resources.
	// ------------------------------------------------------------------
	glfwTerminate();
	return 0;
}

double lastCamAnim = -1;
double lastFlash = -1;
double lastWire = -1;
double lastMove = -1;
// process all input: query GLFW whether relevant keys are pressed/released this frame and react accordingly
// ---------------------------------------------------------------------------------------------------------
void processInput(GLFWwindow *window)
{
	if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
		glfwSetWindowShouldClose(window, true);

	if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
		camera.ProcessKeyboard(FORWARD, deltaTime);
	if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
		camera.ProcessKeyboard(BACKWARD, deltaTime);
	if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
		camera.ProcessKeyboard(LEFT, deltaTime);
	if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
		camera.ProcessKeyboard(RIGHT, deltaTime);

	if (glfwGetKey(window, GLFW_KEY_R) == GLFW_PRESS)
		activateSnow = true;
	if (glfwGetKey(window, GLFW_KEY_R) == GLFW_RELEASE)
		activateSnow = false;
	if (glfwGetKey(window, GLFW_KEY_T) == GLFW_PRESS)
	{
		double now = glfwGetTime();
		if (cameraAnimation == false && now > lastCamAnim + 0.2)
		{
			cameraAnimation = true;
			lastCamAnim = now;
		}
		if (cameraAnimation == true && now > lastCamAnim + 0.2)
		{
			cameraAnimation = false;
			lastCamAnim = now;
		}
	}

	if (glfwGetKey(window, GLFW_KEY_Y) == GLFW_PRESS)
	{
		double now = glfwGetTime();
		if (wireframe == false && now > lastWire + 0.2)
		{
			wireframe = true;
			lastWire = now;
		}
		if (wireframe == true && now > lastWire + 0.2)
		{
			wireframe = false;
			lastWire = now;
		}
	}

	if (glfwGetKey(window, GLFW_KEY_F) == GLFW_PRESS)
	{
		double now = glfwGetTime();
		if (flashlight == false && now > lastFlash + 0.2)
		{
			flashlight = true;
			lastFlash = now;
		}
		if (flashlight == true && now > lastFlash + 0.2)
		{
			flashlight = false;
			lastFlash = now;
		}
	}
	if (glfwGetKey(window, GLFW_KEY_E) == GLFW_PRESS && destroyCube==false)
	{
		moveAnimation = 2.5*deltaTime;
	}
	if (glfwGetKey(window, GLFW_KEY_E) == GLFW_RELEASE && destroyCube==false)
	{
		moveAnimation = 0.0f;
	}
	if (glfwGetKey(window, GLFW_KEY_G) == GLFW_PRESS && closetosword==true)
	{
		sword = true;
	}
	if ((glfwGetKey(window, GLFW_KEY_G) == GLFW_PRESS && closetohammer == true && destroyCube==true))
	{
		hammer = true;
		selectHammer = true;
	}
	if (glfwGetKey(window, GLFW_KEY_LEFT_ALT) == GLFW_PRESS && (sword == true || hammer==true) && attack==false)
	{
		lastAttackTime = (float) glfwGetTime();
		attack = true;
		attackAngle = 0.01f;
		attackBack = false;
	}
	if (glfwGetKey(window, GLFW_KEY_1) == GLFW_PRESS && sword == true && hammer==true)
	{
		selectSword = true;
		selectHammer = false;
	}
	if (glfwGetKey(window, GLFW_KEY_2) == GLFW_PRESS && sword == true && hammer == true)
	{
		selectHammer = true;
		selectSword = false;
	}

	if (glfwGetKey(window, GLFW_KEY_J) == GLFW_PRESS) {

		lightAngle += 0.3f;
		if (lightAngle > 360.0f)
			lightAngle -= 360.0f;
		glm::vec3 lightDirTr = glm::vec3(glm::rotate(glm::mat4(1.0f), glm::radians(lightAngle), glm::vec3(0.0f, 1.0f, 0.0f)) * glm::vec4(lightDir, 1.0f));
		//modelShader.use();
		//modelShader.setVec3("lightDir", lightDirTr);
	}

	if (glfwGetKey(window, GLFW_KEY_L) == GLFW_PRESS) {
		lightAngle -= 0.3f;
		if (lightAngle < 0.0f)
			lightAngle += 360.0f;
		glm::vec3 lightDirTr = glm::vec3(glm::rotate(glm::mat4(1.0f), glm::radians(lightAngle), glm::vec3(0.0f, 1.0f, 0.0f)) * glm::vec4(lightDir, 1.0f));
		//modelShader.use();
		//modelShader.setVec3("lightDir", lightDirTr);
	}

}

// glfw: whenever the window size changed (by OS or user resize) this callback function executes
// ---------------------------------------------------------------------------------------------
void framebuffer_size_callback(GLFWwindow* window, int width, int height)
{
	// make sure the viewport matches the new window dimensions; note that width and 
	// height will be significantly larger than specified on retina displays.
	glViewport(0, 0, width, height);
}

// glfw: whenever the mouse moves, this callback is called
// -------------------------------------------------------
void mouse_callback(GLFWwindow* window, double xpos, double ypos)
{
	if (firstMouse)
	{
		lastX = xpos;
		lastY = ypos;
		firstMouse = false;
	}

	float xoffset = xpos - lastX;
	float yoffset = lastY - ypos; // reversed since y-coordinates go from bottom to top

	lastX = xpos;
	lastY = ypos;

	camera.ProcessMouseMovement(xoffset, yoffset);
}

// glfw: whenever the mouse scroll wheel scrolls, this callback is called
// ----------------------------------------------------------------------
void scroll_callback(GLFWwindow* window, double xoffset, double yoffset)
{
	camera.ProcessMouseScroll(yoffset);
}

// utility function for loading a 2D texture from file
// ---------------------------------------------------
unsigned int loadTexture(char const * path)
{
	unsigned int textureID;
	glGenTextures(1, &textureID);

	int width, height, nrComponents;
	unsigned char *data = stbi_load(path, &width, &height, &nrComponents, 0);
	if (data)
	{
		GLenum format;
		if (nrComponents == 1)
			format = GL_RED;
		else if (nrComponents == 3)
			format = GL_RGB;
		else if (nrComponents == 4)
			format = GL_RGBA;

		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, format, GL_UNSIGNED_BYTE, data);
		glGenerateMipmap(GL_TEXTURE_2D);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		stbi_image_free(data);
	}
	else
	{
		std::cout << "Texture failed to load at path: " << path << std::endl;
		stbi_image_free(data);
	}

	return textureID;
}


// loads a cubemap texture from 6 individual texture faces
// order:
// +X (right)
// -X (left)
// +Y (top)
// -Y (bottom)
// +Z (front) 
// -Z (back)
// -------------------------------------------------------
unsigned int loadCubemap(vector<std::string> faces)
{
	unsigned int textureID;
	glGenTextures(1, &textureID);
	glBindTexture(GL_TEXTURE_CUBE_MAP, textureID);

	int width, height, nrChannels;
	for (unsigned int i = 0; i < faces.size(); i++)
	{
		unsigned char *data = stbi_load(faces[i].c_str(), &width, &height, &nrChannels, 0);
		if (data)
		{
			glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, data);
			stbi_image_free(data);
		}
		else
		{
			std::cout << "Cubemap texture failed to load at path: " << faces[i] << std::endl;
			stbi_image_free(data);
		}
	}
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

	return textureID;
}