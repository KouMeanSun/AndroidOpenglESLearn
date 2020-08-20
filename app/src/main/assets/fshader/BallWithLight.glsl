precision mediump float;
varying vec4 vDiffuse;//接受从顶点着色器过来的散射光分量

void main(){
    vec4 finalColor = vec4(1.0);
    //给此片元着色值
    gl_FragColor = finalColor * vDiffuse + finalColor*vec4(0.15,0.15,0.15,1.0);
}






