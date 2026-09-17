attribute vec4 a_position;
attribute vec3 a_normal;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform mat4 u_proj;
uniform mat4 u_trans;
uniform vec3 u_lightdir;
uniform vec3 u_camdir;
uniform vec3 u_campos;
uniform vec3 u_ambientColor;

uniform vec4 u_sun_info;
uniform vec4 u_planet_info;
uniform float u_alpha;

varying vec4 v_col;
varying vec3 v_position;
varying vec2 v_texCoords;

const vec3 diffuse = vec3(0.0);

void main(){
    v_texCoords = a_texCoord0;
    vec3 specular = vec3(0.0, 0.0, 0.0);

    vec4 worldPosition = u_trans * a_position;
    v_position = worldPosition.xyz;
    
    vec3 norc = u_ambientColor * 0.85; 
    
    v_col=vec4(norc, u_alpha); 
    
    gl_Position = u_proj * worldPosition;
}