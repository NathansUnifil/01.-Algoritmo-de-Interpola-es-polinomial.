public class InterpolaçãoNewton {
    static final double EPS=1e-8;
    double p[];

    InterpolaçãoNewton(double a[]) {
        p=(double[])a.clone(); reduzir();
    }
    void reduzir() {
        int size;
        for(size=p.length-1;size>0 && Math.abs(p[size])<EPS;size--);
        if(++size<p.length) {
            double q[]=new double[size];
            for(int i=0;i<size;i++) q[i]=p[i];
            p=q;
        }
    }
    InterpolaçãoNewton adicionar(InterpolaçãoNewton b) {
        int n=Math.max(this.p.length,b.p.length);
        double q[]=new double[n];
        for(int i=0;i<n;i++) {
            if(i<this.p.length) q[i]+=this.p[i];
            if(i<b.p.length) q[i]+=b.p[i];
        }
        return new InterpolaçãoNewton(q);
    }
    InterpolaçãoNewton multiplica(InterpolaçãoNewton b) {
        double q[]=new double[this.p.length+b.p.length-1];
        for(int i=0;i<this.p.length;i++) for(int j=0;j<b.p.length;j++)
            q[i+j]+=this.p[i]*b.p[j];
        return new InterpolaçãoNewton(q);
    }

    /* Método de interpolação de Newton: para n pares (x,f(x)),
    retorna o polinômio de n graus mais baixo em base decresente na matriz retornada */

    public static double[] newton(double f[],double x[]) {
        InterpolaçãoNewton r=new InterpolaçãoNewton(new double[]{f[0]});
        int n=f.length;
        double a[][]=new double[n][n];
        for(int i=0;i<n;i++) a[0][i]=f[i];
        for(int i=1;i<n;i++) {
            for(int j=0;j<n-i;j++) a[i][j]=(a[i-1][j+1]-a[i-1][j])/(x[j+i]-x[j]);
            InterpolaçãoNewton m=new InterpolaçãoNewton(new double[]{a[i][0]});
            for(int j=0;j<i;j++) {
                m=m.multiplica(new InterpolaçãoNewton(new double[]{-x[j],1}));
            }
            r=r.adicionar(m);
        }
        return r.p;
    }

    public static void main(String[] args) {
        double[] abc = {-1, 0, 1, 2, 3};
        double[] edf = {1, 1, 0, -1, -2};
        System.out.println("Resultado: " + newton(abc, edf));
    }
}
