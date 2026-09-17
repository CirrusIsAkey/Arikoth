#define HIGHP
#define NSCALE 100.0 / 2.0
#define DSCALE 160.0 / 2.0

uniform sampler2D u_texture;
uniform sampler2D u_noise;

uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform float u_time;

varying vec2 v_texCoords;

void main(){
    vec2 c = v_texCoords;
    vec2 coords = vec2(c.x * u_resolution.x + u_campos.x, c.y * u_resolution.y + u_campos.y);

    float btime = u_time / 5000.0;
    float distortionTime = u_time / 2250.0;
    vec4 baseTexture = texture2D(u_texture, c);

    vec2 distortedC = c + (vec2(
    texture2D(u_noise, coords / DSCALE + vec2(distortionTime) * vec2(-0.9, 0.8)).r,
    texture2D(u_noise, coords / DSCALE + vec2(distortionTime * 1.1) * vec2(0.8, -1.0)).r
    ) - vec2(0.5)) * 20.0 / u_resolution;

    vec4 distortedTexture = texture2D(u_texture, distortedC);

    vec3 tintedShimmer = distortedTexture.rgb * vec3(1.0, 0.5412, 0.0510); // blackbody3, check ArikothPal for more info

    vec4 compositeColor = vec4(mix(baseTexture.rgb, tintedShimmer, 0.45), baseTexture.a);

    float wave = abs(sin(coords.x * 0.55 + coords.y * 0.5) + 0.1 * sin(2.5 * coords.x) + 0.15 * sin(3.0 * coords.y)) / 30.0;
    float noiseVal = wave + (texture2D(u_noise, coords / NSCALE + vec2(btime) * vec2(-0.2, 0.8)).r +
    texture2D(u_noise, coords / NSCALE + vec2(btime * 1.1) * vec2(0.8, -1.0)).r) / 2.0;

    if (noiseVal > 0.49 && noiseVal < 0.62) {
        compositeColor.rgb *= 1.07;
    }
    if (noiseVal > 0.54 && noiseVal < 0.57) {
        compositeColor.rgb *= 1.12;
    }

    gl_FragColor = vec4(compositeColor.rgb, min(compositeColor.a * 100.0, 1.0));
}