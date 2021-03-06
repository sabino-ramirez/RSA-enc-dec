package project2;
import java.io.*;
import java.util.Random;
import java.math.BigInteger;
public class template
{

int  primegen()         //generates prime numbers
{
int  i,j,num,size=1000,index=0,c,r,r1;
Random ran = new Random();
int arr []=new int[size];
r1=ran.nextInt(2000);
for(i=100;i<r1;i++)
{
num=i;
c=0;
for(j=2;j<=num/2;j++)
{
 if(num%j==0)
 c++;
}
if(c==0)
arr[index++]=num;
}
r= ran.nextInt(index);   //returns a random prime number
return arr[r];
}

long gcd(long a, long b)  //calculates greatest common divisor
{
    long t;
    while(b != 0)
{
     t = a;
     a = b;
     b = t%b;
}
     return a;
}

long cale(long b)   //calculates and returns e
 {
long a,g;
Random ran = new Random();
do
{
a= ran.nextInt((int)b/2);
g= gcd(a,b);
}
while(g!=1);
return a;
}

long solve(long phi, long e) //extended Eucledian Algorithm to find d
{
    long x1= 1, y1= 0, x2 = 0, y2 = 1, d1=phi,d2=e,k;
     long lastx,lasty,lastd;
     do
{
      k = d1/ d2;
      lastx=x1-(x2*k);
      lasty=y1-(y2*k);
      lastd=d1-(d2*k);
       
      x1=x2;
     x2=lastx;
     y1=y2;
     y2=lasty;
     d1=d2;
     d2=lastd;
     }
   while (lastd!= 1);
   return lasty;
 }

BigInteger[] encrypt(String str,long e,long n)  //Encrypts String
{
int i,l,ch;
char c;
BigInteger charpow,t1,t2,t3;
l=str.length();
BigInteger arr[]=new BigInteger[l];
t2=BigInteger.valueOf(e);    //converting long to BigInteger
t3=BigInteger.valueOf(n);
for(i=0;i<l;i++)
{
c=str.charAt(i);
ch=(int)c;
t1=BigInteger.valueOf(ch);  //Converting Biginteger to integer
charpow=t1.modPow(t2,t3);
arr[i]=charpow;
}
System.out.println("Encrypted string");
for(i=0;i<l;i++)
System.out.print(arr[i]);
System.out.println("");
return arr;
}

void decrypt(BigInteger arr[],long d,long n)   //Decrypts String
{
int l=arr.length,i;
char ch;
BigInteger res,res1,t2,t1;
String str="";
t1=BigInteger.valueOf(d);
t2=BigInteger.valueOf(n);
for(i=0;i<l;i++)
{
res=arr[i];
res1=res.modPow(t1,t2);
ch=(char)(res1.intValue());     //Converting Biginteger to integer
str=str+ch;
}
System.out.println("Decrypted string="+str);
}

public static void main (String[] args) throws IOException
 {
long p,q,initd,d,e, n,phi;
String s;
template rsa = new template();
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
p=rsa.primegen();
q=rsa.primegen();
n=p*q;
phi=(p-1)*(q-1);
e=rsa.cale(phi);
initd=rsa.solve(phi,e);
if(initd>phi)
d=initd%phi;
else
d=initd+phi;
System.out.println("Enter string");
s=br.readLine();
BigInteger ans[]=rsa.encrypt(s,e,n);
rsa.decrypt(ans,d,n);
}
}
