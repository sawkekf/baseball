package baseballRecord;

public class FielderData {
	
	String name;
	int ubb;//볼넷
	int hbp;//사구
	int b1;//안타
	int b2;//2루타
	int b3;//3루타
	int hr;//홈런
	int pa;//타석
	int sf;//희생타
	double average = (double)(b1+b2+b3+hr)/(pa-ubb-hbp-sf);
	double obp = (double)(b1+b2+b3+hr+ubb+hbp)/pa;
	double slg = (double)(b1+2*b2+3*b3+4*hr)/(pa-ubb-hbp-sf);
	double ops = (double)obp+slg;
	double woba = (double)(0.72*ubb + 0.75*hbp + 0.90*b1 + 1.24*b2 + 1.56*b3 + 1.95*hr)/pa;
	
	public FielderData(String name, int ubb, int hbp, int b1, int b2,int b3, int hr, int pa, int sf) {//생성자
		
		this.name = name;
		this.ubb = ubb;
		this.hbp = hbp;
		this.b1 = b1-b2-b3-hr;//장타율 값을 구하기 위해 생성자에서 안타를 1루타 값으로 변환
		this.b2 = b2;
		this.b3 = b3;
		this.hr = hr;
		this.pa = pa;
		this.sf = sf;
		
		
	}
	
	@Override
	public String toString() {
		double average = (double)(b1+b2+b3+hr)/(pa-ubb-hbp-sf);
		double obp = (double)(b1+b2+b3+hr+ubb+hbp)/pa;
		double slg = (double)(b1+2*b2+3*b3+4*hr)/(pa-ubb-hbp-sf);
		double ops = (double)obp+slg;
		double woba = (0.72*ubb + 0.75*hbp + 0.90*b1 + 1.24*b2 + 1.56*b3 + 1.95*hr)/pa;
		return String.format(" 선수명 : %s / 타율 : %.3f / 홈런 : %d / 출루율 : %.3f / 장타율 : %.3f / OPS : %.3f / wOBA : %.3f / 타석 : %d / 1루타 : %d / 2루타 : %d / 3루타 : %d / 볼넷 : %d / 사구 : %d / 희생타 : %d", name,average,hr,obp,slg,ops,woba,pa,b1,b2,b3,ubb,hbp,sf);
	}

	public double getAverage() {//타율 소숫점 3자리 비교를 위해 1000을 곱함
		return (double)(b1+b2+b3+hr)/(pa-ubb-hbp-sf)*1000;
	}

	public double getObp() {//출루율 소숫점 3자리 비교를 위해 1000을 곱함
		return (double)(b1+b2+b3+hr+ubb+hbp)/pa*1000;
	}

	public double getSlg() {//장타율 소숫점 3자리 비교를 위해 1000을 곱함
		return (double)(b1+2*b2+3*b3+4*hr)/(pa-ubb-hbp-sf)*1000;
	}

	public double getOps() {//OPS 소숫점 3자리 비교를 위해 1000을 곱함
		return (double)this.getObp()+this.getSlg();
	}

	public double getWoba() {//wOBA 소숫점 3자리 비교를 위해 1000을 곱함
		return (0.72*ubb + 0.75*hbp + 0.90*b1 + 1.24*b2 + 1.56*b3 + 1.95*hr)/pa*1000;
	}

	

}
