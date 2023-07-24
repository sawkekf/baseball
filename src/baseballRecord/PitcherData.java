package baseballRecord;

public class PitcherData implements Comparable<PitcherData> {

	String name;
	int ubb;//볼넷
	int hbp;//사구
	int hr;//홈런
	double ip;//이닝
	double ip2;//이닝 소수값 치환
	int k;//삼진
	int r;//실점
	int rr;//자책점
	int w; //승
	int l;//패
	double fip = (13 * hr +3 * ubb + hbp - 2 * k) / ip + 1.05;//FIP
	double era = (9*rr)/ip;//평균자책점
	
	PitcherData(String name, int ubb, int hbp, int hr, double ip, int k, int r, int rr, int w, int l){//생성자
		
		this.name = name;
		this.ubb = ubb;
		this.hbp = hbp;
		this.hr = hr;
		this.ip = ip;
		this.k = k;
		this.r = r;
		this.rr = rr;
		this.w = w;
		this.l = l;
		
	}
	
	@Override
	public String toString() {
		double era = (9*rr)/ip;
		double fip = (13 * hr +3 * (ubb + hbp) - 2 * k) / ip + 3.28;
		return String.format(" 선수명 : %s / 승 : %d / 패 : %d / 이닝 : %.1f / 평균자책점 : %.2f / FIP : %.2f / ubb : %d / hbp : %d / r : %d / rr : %d / hr : %d / k : %d", name,w,l,ip,era,fip,ubb,hbp,r,rr,hr,k);
	}

	@Override
	public int compareTo(PitcherData pit) {
		return name.compareTo(name);
		
	}

	public double getFip() {//소숫점 두자리 비교를 위해 FIP에 100을 곱함
		return ((13 * hr +3 * ubb + hbp - 2 * k) / ip + 1.05)*100;
	}


	public double getEra() {//소숫점 두자리 비교를 위해 평균자책점에 100을 곱함
		return (9*rr)/ip*100;
	}

	
	
	
}
