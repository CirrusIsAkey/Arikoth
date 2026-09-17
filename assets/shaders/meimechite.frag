#define HIGHP

#define S1 vec3(0.196, 0.137, 0.098)
#define S2 vec3(0.392, 0.275, 0.196)
#define S3 vec3(0.706, 0.529, 0.392)

#define INV_NSCALE 0.00200
#define INV_SWIRL  0.00190
#define TAU        6.28318530718

#define BAND(minVal, maxVal, val) (step(minVal, val) - step(maxVal, val))

const vec3 LUM_WEIGHT = vec3(0.299, 0.587, 0.114);

uniform sampler2D u_texture;
uniform sampler2D u_noise;

uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform float u_time;

varying vec2 v_texCoords;

vec3 getWarmColors(float factor) {
    vec3 col = 0.5 + 0.5 * cos(TAU * (factor * vec3(0.3, 0.2, 0.1) + vec3(0.0, 0.1, 0.2)));
    return col * vec3(0.19, 0.09, 0.02) + vec3(0.12, 0.05, 0.02);
}

void main() {
    vec2 c = v_texCoords.xy;

    vec2 coords = c * u_resolution + u_campos;

    float btime = u_time * 0.000010;
    float btime105 = btime * 1.05;

    vec2 swirlBase = coords * INV_SWIRL;
    vec2 swirlOffset = vec2(
            texture2D(u_noise, swirlBase + btime * 0.25).r,
            texture2D(u_noise, swirlBase - btime * 0.15).r
    ) - 0.5;

    vec2 warpedUV = (coords + swirlOffset * 80.0) * INV_NSCALE;

    float n1 = texture2D(u_noise, warpedUV + btime * vec2(-0.4, 0.3)).r;
    float n2 = texture2D(u_noise, warpedUV + btime105 * vec2(-0.35, -0.45)).r;
    float n3 = texture2D(u_noise, warpedUV + btime105 * vec2(0.45, -0.22)).r;

    float noise1 = (n1 + n2) * 0.5;
    float noise2 = (n1 + n3) * 0.5;

    vec2 depthGradient = vec2(n1 - n2, n1 - n3);
    float gradSq = dot(depthGradient, depthGradient) * 9.0;
    float normalZ = inversesqrt(gradSq + 1.0);

    float rimFactor = 1.0 - normalZ;
    float edgeLight = step(0.32, rimFactor) * 0.6 + step(0.48, rimFactor) * 0.4;

    float mask1_S2 = BAND(0.505, 0.575, noise1);
    float mask1_warm = BAND(0.575, 0.605, noise1);
    float mask1_S1 = BAND(0.430, 0.670, noise1) - (mask1_S2 + mask1_warm);

    vec3 warmShift = getWarmColors(noise1 * 0.6 + btime);

    vec3 additiveEffect = S2 * (mask1_S2 * 0.90)
    + warmShift * (mask1_warm * 0.75)
    + S1 * (mask1_S1 * 0.95);

    float mask2_S3 = BAND(0.505, 0.575, noise2);
    float mask2_S1 = BAND(0.430, 0.670, noise2) - mask2_S3;

    additiveEffect += S3 * (mask2_S3 * 0.90) + S1 * (mask2_S1 * 0.95);
    additiveEffect += vec3(0.95, 0.45, 0.15) * (edgeLight * 0.25);

    vec2 glossLightDir = vec2(0.5, 0.866);
    float rawSpec = max(dot(-depthGradient * 3.0, glossLightDir) + 0.5, 0.0);
    float specHighlight = step(0.72, rawSpec) * 0.5 + step(0.88, rawSpec) * 0.5;

    float glossSpeed = u_time * 0.0020;

    float sweepPhase = fract((coords.x * 0.0015 + coords.y * 0.0025) - glossSpeed);

    float bar1 = BAND(0.10, 0.12, sweepPhase);
    float bar2 = BAND(0.15, 0.20, sweepPhase);
    float bar3 = BAND(0.24, 0.38, sweepPhase);
    float bar4 = BAND(0.42, 0.44, sweepPhase);

    float multiBarSheen = bar1 * 0.50 + bar2 * 0.75 + bar3 * 1.00 + bar4 * 0.50;

    vec3 glossTint = vec3(0.95, 0.70, 0.48) * (specHighlight * 0.35 + multiBarSheen * 0.22);

    additiveEffect += glossTint;

    vec4 color = texture2D(u_texture, c);
    color.rgb += additiveEffect * 0.70;

    color.rgb = max(color.rgb - 0.005, 0.0);
    color.rgb = pow(color.rgb, vec3(1.20));

    float lum = dot(color.rgb, LUM_WEIGHT);
    color.rgb = mix(vec3(lum), color.rgb, 1.40);

    gl_FragColor = color;
}