class Test {
    public void method1() {
        int n = 0;
        double z = 1.0f;        
        for(n = 0; n < 10; n++) {
            z += 1.0f;
        }
    }
    public void method2() {
        int n = 0;
        double z = 1.0f;        
        for(n = 0; n < 10; n++) {
            z = z + 1.0f;
        }
    }
    public void method3() {
        int n = 0;
        double z = 1.0f;        
        for(n = 0; n < 10; n++) {
            z += 1.0f;
        }
    }
    public static void main(String[] args) {
        Test t = new Test();
        t.method1();
        t.method2();    
    }
}
