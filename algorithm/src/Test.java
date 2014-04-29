import java.text.DecimalFormat;
import java.util.Scanner;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//输入
		Scanner in=new Scanner(System.in);
		int n=in.nextInt();
		int m=in.nextInt();
		double[][] A=new double[n+m+1][n+m+1];
		double[] b=new double[n+m+1];
		double[] c=new double[n+m+1];
		double v=0;
		for(int i=1;i<=n;i++){
			c[i]=in.nextDouble();
			}
		v=in.nextDouble();  //存v的值
		for(int i=1;i<=m;++i){
			b[i+n]=in.nextDouble();
			for(int j=1;j<=n;++j){
				A[i+n][j]=in.nextDouble();
			}
		}
		
		//调用单纯形算法
		Simplex simplex=new Simplex(n,m);
		double[] Solution=simplex.ComputeSimplex(A, b, c,v);
		
		//输出结果
		DecimalFormat df=new DecimalFormat("0.00");
		System.out.println("the Solution is：");
		for(int i=1;i<=n;++i){
			System.out.print("x"+i+"="+df.format(Solution[i])+" ");
		}
		System.out.println();
		System.out.println("Optimal Objective function value: ");
		System.out.println("z = "+df.format(Solution[n+m+1]));
		
	}

}
