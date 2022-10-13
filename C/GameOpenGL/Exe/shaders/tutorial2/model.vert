#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNormal;
layout (location = 2) in vec2 aTexCoords;


vec3 FragPos;
vec3 Normal;
vec2 TexCoords;
out vec3 FogFragPos;
 
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform bool reverse_normals;

void main()
{
    FragPos = vec3(model * vec4(aPos, 1.0));
    if(reverse_normals) // a slight hack to make sure the outer large cube displays lighting from the 'inside' instead of the default 'outside'.
        Normal = transpose(inverse(mat3(model))) * (-1.0 * aNormal);
    else
        Normal = transpose(inverse(mat3(model))) * aNormal;
    TexCoords = aTexCoords;

	FogFragPos = vec3(view * model * vec4(aPos, 1.0));
	
    gl_Position = projection * view * model * vec4(aPos, 1.0);
}