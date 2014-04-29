import java.util.HashSet;
import java.util.Set;


public class Simplex {
	private int n;
	private int m;
	public Simplex(int n,int m){
		this.n=n;
		this.m=m;
	}
	
	public double[] ComputeSimplex(double[][] A,double[] b,double[] c,double v){
		Set<Integer>N=new HashSet<Integer>();
		Set<Integer>B=new HashSet<Integer>();
		for(int i=1;i<=n;++i)
			N.add(i);
		for(int i=n+1;i<=n+m;i++)
			B.add(i);
		v=Initialize(N,B,A,b,c,v);//预处理	
		return getSolution(N,B,A,b,c,v); //计算出结果
	}
	
	private int PositiveIndex(Set<Integer>N,double[] c){
		for(Integer i:N){
			if(c[i]>0)
				return i;
		}
		return -1;
	}
	//迭代计算
	private double[] getSolution(Set<Integer>N,Set<Integer>B,double[][] A,double[] b,double[] c,double v){
		double[] Solution=new double[n+m+2];
		int j=PositiveIndex(N,c);
		while(j>=0){
			double Delta=100000;
			int l=-1;
			for(Integer i:B){
				if(A[i][j]>0&&b[i]/A[i][j]<Delta){
					Delta=b[i]/A[i][j];
					l=i;
				}
			}
			if(l==-1){
				System.out.println("unbounded!");
				System.exit(0);
			}else{
				v=Pivot(N,B,A,b,c,v,l,j);
			}
			j=PositiveIndex(N,c);
		}
		for(int i=0;i<Solution.length;++i){
			if(B.contains(i)){
				Solution[i]=b[i];
			}
			else
				Solution[i]=0;
		}
		Solution[n+m+1]=v;
		return Solution;	
	}

	//预处理
	private double Initialize(Set<Integer>N,Set<Integer>B,double[][] A,double[] b,double[] c,double v){
		int index=1;
		double min=b[1];
		for(int i=1;i<b.length;++i){
			if(b[i]<min){
				min=b[i];
				index=i;
			}
		}
		if(min>=0)
			return v;

		
		//构造 Laux
		N.add(0);
		for(int i=n+1;i<=n+m;++i){
			A[i][0]=-1;
		}
		double[] c1=new double[b.length];
		c1[0]=-1;
		double v1=0;
		v1=Pivot(N,B,A,b,c1,v1,index,0);
		double[] Solution=getSolution(N,B,A,b,c1,v1);
		if(Solution[0]!=0){
			System.out.println("infeasible!");
			System.exit(0);
		}
		
		//恢复标准形式
		N.remove(0);
		B.remove(0);
		for(int e=1;e<=n;++e){
			if(c[e]!=0&&B.contains(e)){
				v=v+c[e]*b[e];
				for(Integer j:N){
					c[j]=c[j]-c[e]*A[e][j];
				}
				c[e]=0;
			}
		}
		return v;
	}
	//交换变元
	private double Pivot(Set<Integer>N,Set<Integer>B,double[][] A,double[] b,double[] c,double v,int l,int e){
		b[e]=b[l]/A[l][e];
		N.remove(e);
		for(Integer j:N){
			A[e][j]=A[l][j]/A[l][e];
		}
		A[e][l]=1/A[l][e];
		B.remove(l);
		for(Integer i:B){
			b[i]=b[i]-A[i][e]*b[e];
			for(Integer j:N){
				A[i][j]=A[i][j]-A[i][e]*A[e][j];
			}
			A[i][l]=-A[i][e]*A[e][l];
		}
		v=v+c[e]*b[e];
		for(Integer j:N){
			c[j]=c[j]-c[e]*A[e][j];
		}
		c[l]=-c[e]*A[e][l];
		N.add(l);
		B.add(e);
		return v;
	}
}
