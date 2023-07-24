package baseballRecord;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Run {
	
	public static void main(String[] args) throws IOException {
	
		Scanner sc = new Scanner(System.in); // 스캐너
		
		PitcherMap pitchermap = new PitcherMap(); // 투수 map 클래스
		
		FielderMap fieldermap = new FielderMap(); // 타자 map 클래스
		
		File file =new File("src\\baseballRecord\\files\\backup.txt"); //투수 백업 텍스트 파일
		
		File file2 =new File("src\\baseballRecord\\files\\backup2.txt"); // 타자 백업 텍스트 파일
		
		FileReader freader = new FileReader(file); 
		
		BufferedReader breader = new BufferedReader(freader); // 투수 백업파일 리더
		
		FileReader freader2= new FileReader(file2);
		
		BufferedReader breader2 = new BufferedReader(freader2); // 타자 백업 파일 리더
		
		String str;
		
		while((str=breader.readLine())!=null) { //리딩한 투수 텍스트를 쪼개서 클래스에 넣을 변수 만들기
			
			String[]a = str.split("/"); // 스플릿으로 쪼갬
			
			String key = a[0].trim().substring(0); // 투수 map에 넣을 key값
			
			String name = a[1].trim().substring(6); //선수명
			
			int w = Integer.parseInt(a[2].trim().substring(4)); //승
			
			int l = Integer.parseInt(a[3].trim().substring(4)); //패
			
			double ip = Double.parseDouble(a[4].trim().substring(4)); //이닝
			
			int ubb = Integer.parseInt(a[7].trim().substring(6)); // 볼넷
		
			int hbp = Integer.parseInt(a[8].trim().substring(6)); //사구
			
			int r = Integer.parseInt(a[9].trim().substring(4)); //실점
		
			int rr = Integer.parseInt(a[10].trim().substring(5)); //자책점
			
			int hr = Integer.parseInt(a[11].trim().substring(5)); // 홈런
			
			int k = Integer.parseInt(a[12].trim().substring(4)); //삼진
			
			
			for(int i=0; i<a.length; i++) {
				
				PitcherData pi = new PitcherData(name, ubb, hbp, hr, ip, k, r, rr, w, l); //만든 변수를 반복문을 통해 value값으로 사용할 투수 데이터 클래스에 입력
				
				pitchermap.pmap.put(key, pi); // 키값과 밸류값을 투수 맵에 입력
					
			}
			

		}
		
		String str1;
		
		while((str1=breader2.readLine())!=null) { //리딩한 타자 텍스트를 쪼개서 클래스에 넣을 변수 만들기
			
			String[] a = str1.split("/"); //스플릿으로 쪼갬
			
			String key = a[0].trim().substring(0); // 타자 map에 넣을 key값
			
			String name = a[1].trim().substring(6); // 선수명
			
			int hr = Integer.parseInt(a[3].trim().substring(5)); //홈런
		
			int pa = Integer.parseInt(a[8].trim().substring(5)); //타석
		
			int b1 = Integer.parseInt(a[9].trim().substring(6)); //1루타
		
			int b2 = Integer.parseInt(a[10].trim().substring(6)); //2루타
			
			int b3 = Integer.parseInt(a[11].trim().substring(6)); //3루타
			
			int ubb = Integer.parseInt(a[12].trim().substring(5)); //볼넷
		
			int hbp = Integer.parseInt(a[13].trim().substring(5)); //사구
		
			int sf = Integer.parseInt(a[14].trim().substring(6)); //희생타
		
			
			for(int i=0; i<a.length; i++) {
				
				FielderData fi = new FielderData(name, ubb, hbp, b1+b2+b3+hr, b2, b3, hr, pa, sf); 
				
				// 만든 변수를 반복문을 통해 value값으로 사용할 타자 데이터 클래스에 입력 
				//이때 b1값은 FielderData 클래스 내부에서 
				// b1 = b1-b2-b3-hr으로 계산됨으로
				// b1을 입력할 때는 b1+b2+b3+hr으로 입력을 요함
				
				fieldermap.fmap.put(key, fi); // 키값과 밸류값을 타자 맵에 입력
			
			}
			
		}
		
		freader.close();
		breader.close();
		freader2.close();
		breader2.close(); // 리더 종료
		
		while(true) {
			
			System.out.println("===============================================");
			System.out.println("======KBO Player Record Searching Program======");
			System.out.println("===============================================");
			System.out.println();
			System.out.println("                     menu");
			System.out.println();
			System.out.printf("          1.선수 기록 검색  2.전체 기록 조회 \n\n          3. 선수 정보 수정 4. 선수 기록 삭제");
			System.out.println();
			System.out.println();
			System.out.println("===============================================");
			System.out.println("===============================================");
			
			
			try{// menu 입력값이 정수가 아닐 시 생기는 InputException을 해결하기 위한 예외처리
				int menu = sc.nextInt();
			
			if(menu==1) {
				System.out.println("           어떤 선수를 조회하시겠습니까?");
				System.out.println();
				System.out.println("               1.투수    2.타자");
				menu = sc.nextInt();
				if(menu==1) {
					System.out.println("    어떤 투수를 조회하시겠습니까?(ex. 롯데_43번)");
					System.out.println();
					String searchPitcher = sc.next();
					
					if(pitchermap.pmap.keySet().stream().toList().contains(searchPitcher)!=true
						&&searchPitcher.split("").length<9
						&&searchPitcher.split("").length>4) {
						//투수 맵의 키값을 리스트로 바꾼 이후 내부 키값들이 검색값과 비교하여 일치하지 않는 조건
						//+ 검색 양식을 맞추기 위한 글자수 5~8자 제한
						System.out.println("선수를 추가하시겠습니까?(Y Or N)");
						String yesOrNo = sc.next().toUpperCase(); // uppercase로 대문자 변환
						if(yesOrNo.equals("Y")) {
							System.out.println();
							System.out.println("선수의 이름과 기록을 입력해주세요"); // 스캐너를 통해 객체에 들어갈 변수값 정의
							System.out.println("이름 : ");
							String name = sc.next();
							
							if( fieldermap.fmap.get(searchPitcher)==null) {
								
								
							}else if(!fieldermap.fmap.get(searchPitcher).name.equals(name) 
									&&fieldermap.fmap.get(searchPitcher)!=null) {
								
								System.out.println("동일 팀 및 배번 선수의 이름이 다릅니다.");
								System.out.println();
								continue; // while true문 처음으로 되돌아감
								
							}
							
							System.out.println("이닝 : ");
							double ip = sc.nextDouble();
							System.out.println("승 : ");
							int w = sc.nextInt();
							System.out.println("패 : ");
							int l = sc.nextInt();
							System.out.println("삼진 : ");
							int k = sc.nextInt();
							System.out.println("볼넷 : ");
							int ubb = sc.nextInt();
							System.out.println("사구 : ");
							int hbp = sc.nextInt();
							System.out.println("홈런 : ");
							int hr = sc.nextInt();
							System.out.println("실점 : ");
							int r = sc.nextInt();
							System.out.println("자책점 : ");
							int rr = sc.nextInt();
							
							
							PitcherData pitcherdata = new PitcherData(name, ubb, hbp, hr, ip, k, r, rr, w, l); //변수값을 투수 데이터 클래스에 입력
							
							pitchermap.pmap.put(searchPitcher, pitcherdata); // 투수맵에 스캐너로 입력한 값을 key값으로 투수 데이터 클래스를 value로 입력
							
							
							FileWriter fwrite = new FileWriter(file); //writer를 통해 while true문 내부에 존재하는 map 데이터를 문자열로 입력
							
							for(String key : pitchermap.pmap.keySet()) {
								
								PitcherData a = pitchermap.pmap.get(key);
									
								fwrite.write(key+" / "+a+"\n");
								}
							
							
							fwrite.flush();
							fwrite.close(); //writer 종료
							
							System.out.println("입력한 투수 정보가 추가되었습니다");
							System.out.println();
							System.out.println(searchPitcher+" "+pitchermap.pmap.get(searchPitcher)); // 등록한 투수 정보 출력
							
							
							
						}else if(yesOrNo.equals("N")) { //n을 입력하면 초기 화면으로 돌아감
							
							System.out.println("초기 화면으로 돌아갑니다."); 
							
							
						}else { //y와 n 이외를 입력하면 error를 출력하며 초기 화면으로 돌아감
							
							System.out.println("                   Error!!");
							
							
						}
						
					}else if(pitchermap.pmap.keySet().stream().toList().contains(searchPitcher)==true) {//맵의 key값이 입력받은 투수정보와 일치하면 투수의 정보를 돌려줌
						System.out.println(pitchermap.pmap.get(searchPitcher));
						System.out.println();
						
					}else {//입력한 문자열의 글자수가 5~8자가 아닐 경우 일어나는 에러
						System.out.println("                   Error!!");
						System.out.println();
					}
				}
				
				else if(menu==2) {
					System.out.println("    어떤 타자를 조회하시겠습니까?(ex. 롯데_43번)");
					System.out.println();
					String searchHitter = sc.next();//타자의 팀명, 및 배번 입력
					if(fieldermap.fmap.keySet().stream().toList().contains(searchHitter)!=true&&searchHitter.split("").length<9&&searchHitter.split("").length>4) {
						
						//타자 맵의 키값을 리스트로 바꾼 이후 내부 키값들이 검색값과 비교하여 일치하지 않는 조건 + 검색 양식을 맞추기 위한 글자수 5~8자 제한 
						
						System.out.println("선수를 추가하시겠습니까?(Y Or N)");
						String yesOrNo = sc.next().toUpperCase(); // uppercase로 대문자 변환
						if(yesOrNo.equals("Y")) {
							System.out.println(); 
							System.out.println("선수의 이름과 기록을 입력해주세요");
							System.out.println("이름 : ");
							String name = sc.next(); // 스캐너를 통해 객체에 들어갈 변수값 정의
							
							if(pitchermap.pmap.get(searchHitter)==null) {
									
								}else if(!pitchermap.pmap.get(searchHitter).name.equals(name) //동일 팀 동일 배번인데 등록된 타자가 동명 선수가 아닐 경우 
										&&pitchermap.pmap.get(searchHitter)!=null) {
									System.out.println("동일 팀 및 배번 선수의 이름이 다릅니다.");
									continue;
								}
							
							System.out.println("타석 : ");
							int pa = sc.nextInt();
							System.out.println("안타 : ");
							int b1 = sc.nextInt();
							System.out.println("2루타 : ");
							int b2 = sc.nextInt();
							System.out.println("3루타 : ");
							int b3 = sc.nextInt();
							System.out.println("홈런 : ");
							int hr = sc.nextInt();
							System.out.println("희생타 : ");
							int sf = sc.nextInt();
							System.out.println("볼넷");
							int ubb = sc.nextInt();
							System.out.println("몸에 맞는 공");
							int hbp = sc.nextInt();
							
							FielderData fielderData = new FielderData(name, ubb, hbp, b1, b2, b3, hr, pa, sf); // 스캐너로 입력받은 변수값을 타자 데이터 클래스에 입력
							
							fieldermap.fmap.put(searchHitter, fielderData); // 타자map에 검색한 타자의 팀 및 배번을 key값으로 타자 데이터 클래스를 value값으로 입력
							
							FileWriter fwrite = new FileWriter(file2); // 타자 map값을 writer를 통해 text파일에 문자열로 입력
							
							
								for(String key : fieldermap.fmap.keySet()) {
								
								FielderData a = fieldermap.fmap.get(key);
									
								fwrite.write(key+" / "+a+"\n");
								}
							
							fwrite.flush();
							fwrite.close(); //writer종료
							
							System.out.println("입력한 타자 정보가 추가되었습니다");
							System.out.println();
							System.out.println(searchHitter+" "+fieldermap.fmap.get(searchHitter));
							
						}else if(yesOrNo.equals("N")) { //n을 눌렀을 시 초기화면으로 돌아갑니다 출력
							
							System.out.println("초기 화면으로 돌아갑니다.");
							
						}else {//y와 n이 아닌 다른 키를 눌렀을 때 error를 출력
							
							System.out.println("                   Error!!");
							System.out.println();
							
						}
						
					}else if(fieldermap.fmap.keySet().stream().toList().contains(searchHitter)==true) {
						
						//map의 키값 중 하나와 검색값이 동일하면 검색값(key값)의 value값인 타자 데이터를 출력
						
						System.out.println(fieldermap.fmap.get(searchHitter));
						System.out.println();
						System.out.println();
						
					}else { //글자수가 5~8자가 아닐 경우 출력
						System.out.println("                   Error!!");
						System.out.println();
					}
					
				} else { // 투수 타자 선택의 1, 2가 아닌 다른 값을 입력했을 시 error 출력
					
					System.out.println("                   Error!!");
					System.out.println();
					
				}
				
			}else if(menu==2) { // 여태까지 등록한 모든 선수 전체 성적 조회
				
				System.out.println();
				System.out.println("                전체 성적 조회");
				System.out.println();
				System.out.println("           어떤 선수를 조회하시겠습니까?");
				System.out.println();
				System.out.println("               1.투수    2.타자");
				System.out.println();
				menu=sc.nextInt();
				if(menu==1) {// 1 입력시 투수 성적 조회
					System.out.println();
					System.out.println("           어떤 정렬 기준을 선택하시겠습니까?");
					System.out.println();
					System.out.printf("      1.승 2.패 3.이닝 4.삼진 5.평균자책점 6.FIP \n         7.볼넷 8.사구 9.실점 10.자책점 11.피홈런");
					System.out.println();
					menu=sc.nextInt();
					if(menu==1) { //1 입력시 투수 승리 기준으로 정렬, 선출력값이 높은 값으로 정렬
						
						for(int i = 100; i>=0; i--) {
							
							for(String key : pitchermap.pmap.keySet()) {
								
								
								PitcherData a = pitchermap.pmap.get(key);
								
								if(a.w==i) {
									System.out.println(key+" : "+a);
								}
							
							}
						}
						System.out.println();
					}else if(menu==2) { // 2 입력시 투수 패배를 기준으로 정렬, 선출력값이 높은 값으로 정렬
						for(int i = 100; i>=0; i--) {
							for(String key : pitchermap.pmap.keySet()) {
							
								PitcherData a = pitchermap.pmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double era = (9*a.rr)/a.ip;
										double fip = (13 * a.hr +3 * (a.ubb + a.hbp) - 2 * a.k) / a.ip + 3.28;
										return String.format(" 선수명 : %s / 패 : %d / 승 : %d / 이닝 : %.1f / 평균자책점 : %.2f / FIP : %.2f / ubb : %d /"
												+ " hbp : %d / r : %d / rr : %d / hr : %d / k : %d", a.name,a.l,a.w,a.ip,era,fip,a.ubb,a.hbp,a.r,a.rr,a.hr,a.k);
									}
								}.toString();
									
								if(a.l==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if (menu==3) { 
						
						// 3 입력시 투수 이닝을 기준으로 정렬, ip의 출력되는 값이 소수점 1자리의 double이므로 소수점 비교를 위해 10값을 곱해주고 i값과 비교해준다, 선출력값이 높은 값으로 정렬 
						
						for(int i = 3000; i>=0; i--) {
							
							for(String key : pitchermap.pmap.keySet()) {
							
								PitcherData a = pitchermap.pmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double era = (9*a.rr)/a.ip;
										double fip = (13 * a.hr +3 * (a.ubb + a.hbp) - 2 * a.k) / a.ip + 3.28;
										return String.format(" 선수명 : %s / 이닝 : %.1f / 승 : %d / 패 : %d / 평균자책점 : %.2f / FIP : %.2f / ubb : %d /"
												+ " hbp : %d / r : %d / rr : %d / hr : %d / k : %d", a.name,a.ip,a.w,a.l,era,fip,a.ubb,a.hbp,a.r,a.rr,a.hr,a.k);
									}
								}.toString();
									
								if((int)a.ip*10==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if (menu==4) {  // 4 입력시 투수 삼진을 기준으로 정렬, 선출력값이 높은 값으로 정렬
						for(int i = 300; i>=0; i--) {
							for(String key : pitchermap.pmap.keySet()) {
							
								PitcherData a = pitchermap.pmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double era = (9*a.rr)/a.ip;
										double fip = (13 * a.hr +3 * (a.ubb + a.hbp) - 2 * a.k) / a.ip + 3.28;
										return String.format(" 선수명 : %s / k : %d / 승 : %d / 패 : %d / 평균자책점 : %.2f / FIP : %.2f / ubb : %d /"
												+ " hbp : %d / r : %d / rr : %d / hr : %d / 이닝 : %.1f", a.name,a.k,a.w,a.l,era,fip,a.ubb,a.hbp,a.r,a.rr,a.hr,a.ip);
									}
								}.toString();
									
								if(a.k==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if (menu==5) { 
						
						// 5 입력시 투수 평균자책점을 기준으로 정렬, era는 소수점 2개의 값이므로, 
						//소수점 2개 분을 비교하기 위해getEra는 ear에 100을 곱한 값을 얻는 메서드로 설정되어있다.
						//선출력값이 낮은 값으로 정렬
						
						for(int i = 0; i<30000; i++) {
							for(String key : pitchermap.pmap.keySet()) {
							
								PitcherData a = pitchermap.pmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double era = (9*a.rr)/a.ip;
										double fip = (13 * a.hr +3 * (a.ubb + a.hbp) - 2 * a.k) / a.ip + 3.28;
										return String.format(" 선수명 : %s / 평균자책점 : %.2f / 승 : %d / 패 : %d / k : %d / FIP : %.2f / ubb : %d /"
												+ " hbp : %d / r : %d / rr : %d / hr : %d / 이닝 : %.1f", a.name,era,a.w,a.l,a.k,fip,a.ubb,a.hbp,a.r,a.rr,a.hr,a.ip);
									}
								}.toString();
									
								if((int)a.getEra()==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if (menu==6) {
						
						// 6 입력시 투수 평균자책점을 기준으로 정렬, Fip는 소수점 2개의 값이므로, 
						//소수점 2개 분을 비교하기 위해getFip는 Fip에 100을 곱한 값을 얻는 메서드로 설정되어있다.
						//선출력값이 낮은 값으로 정렬
						
						for(int i = 0; i<30000; i++) {
							for(String key : pitchermap.pmap.keySet()) {
							
								PitcherData a = pitchermap.pmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double era = (9*a.rr)/a.ip;
										double fip = (13 * a.hr +3 * (a.ubb + a.hbp) - 2 * a.k) / a.ip + 3.28;
										return String.format(" 선수명 : %s / FIP : %.2f / 승 : %d / 패 : %d / k : %d / 평균자책점 : %.2f / ubb : %d /"
												+ " hbp : %d / r : %d / rr : %d / hr : %d / 이닝 : %.1f", a.name,fip,a.w,a.l,a.k,era,a.ubb,a.hbp,a.r,a.rr,a.hr,a.ip);
									}
								}.toString();
									
								if((int)(a.getFip())==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==7) {
						
						for(int i = 300; i>=0; i--) {
							for(String key : pitchermap.pmap.keySet()) {
								
								PitcherData a = pitchermap.pmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double era = (9*a.rr)/a.ip;
										double fip = (13 * a.hr +3 * (a.ubb + a.hbp) - 2 * a.k) / a.ip + 3.28;
										return String.format(" 선수명 : %s / ubb : %d / 승 : %d / 패 : %d / k : %d / 평균자책점 : %.2f / FIP : %.2f /"
												+ " hbp : %d / r : %d / rr : %d / hr : %d / 이닝 : %.1f", a.name,a.ubb,a.w,a.l,a.k,era,fip,a.hbp,a.r,a.rr,a.hr,a.ip);
									}
								}.toString();
								
								if(a.ubb==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==8) {
						
						for(int i = 100; i>=0; i--) {
							for(String key : pitchermap.pmap.keySet()) {
								
								PitcherData a = pitchermap.pmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double era = (9*a.rr)/a.ip;
										double fip = (13 * a.hr +3 * (a.ubb + a.hbp) - 2 * a.k) / a.ip + 3.28;
										return String.format(" 선수명 : %s / hbp : %d / 승 : %d / 패 : %d / k : %d / 평균자책점 : %.2f / FIP : %.2f /"
												+ " ubb : %d / r : %d / rr : %d / hr : %d / 이닝 : %.1f", a.name,a.hbp,a.w,a.l,a.k,era,fip,a.ubb,a.r,a.rr,a.hr,a.ip);
									}
								}.toString();
								
								if(a.hbp==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==9) {
						
						for(int i = 300; i>=0; i--) {
							for(String key : pitchermap.pmap.keySet()) {
								
								PitcherData a = pitchermap.pmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double era = (9*a.rr)/a.ip;
										double fip = (13 * a.hr +3 * (a.ubb + a.hbp) - 2 * a.k) / a.ip + 3.28;
										return String.format(" 선수명 : %s / r : %d / 승 : %d / 패 : %d / k : %d / 평균자책점 : %.2f / FIP : %.2f /"
												+ " ubb : %d / hbp : %d / rr : %d / hr : %d / 이닝 : %.1f", a.name,a.r,a.w,a.l,a.k,era,fip,a.ubb,a.hbp,a.rr,a.hr,a.ip);
									}
								}.toString();
								
								if(a.r==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==10) {
						
						for(int i = 300; i>=0; i--) {
							for(String key : pitchermap.pmap.keySet()) {
								
								PitcherData a = pitchermap.pmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double era = (9*a.rr)/a.ip;
										double fip = (13 * a.hr +3 * (a.ubb + a.hbp) - 2 * a.k) / a.ip + 3.28;
										return String.format(" 선수명 : %s / rr : %d / 승 : %d / 패 : %d / k : %d / 평균자책점 : %.2f / FIP : %.2f /"
												+ " ubb : %d / hbp : %d / r : %d / hr : %d / 이닝 : %.1f", a.name,a.rr,a.w,a.l,a.k,era,fip,a.ubb,a.hbp,a.r,a.hr,a.ip);
									}
								}.toString();
								
								if(a.rr==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==11) {
						
						for(int i = 300; i>=0; i--) {
							for(String key : pitchermap.pmap.keySet()) {
								
								PitcherData a = pitchermap.pmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double era = (9*a.rr)/a.ip;
										double fip = (13 * a.hr +3 * (a.ubb + a.hbp) - 2 * a.k) / a.ip + 3.28;
										return String.format(" 선수명 : %s / hr : %d / 승 : %d / 패 : %d / k : %d / 평균자책점 : %.2f / FIP : %.2f /"
												+ " ubb : %d / hbp : %d / r : %d / rr : %d / 이닝 : %.1f", a.name,a.hr,a.w,a.l,a.k,era,fip,a.ubb,a.hbp,a.r,a.rr,a.ip);
									}
								}.toString();
								
								if(a.hr==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else {
						System.out.println("                   Error!!");
						System.out.println();
					}
					
				}else if(menu==2) { //타자 성적 전체 조회
					System.out.println();
					System.out.println("           어떤 정렬 기준을 선택하시겠습니까?");
					System.out.println();
					System.out.printf("      1.홈런 2.타율 3.출루율 4.장타율 5.OPS 6.wOBA\n 7.타석 8.1루타 9.2루타 10.3루타 11.볼넷 12.사구 13.희생타");
					System.out.println();
					menu=sc.nextInt();
					if(menu==1) { // 1을 누르면 홈런을 기준으로 정렬 선출력값이 높은 값으로 정렬
						for(int i = 100; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double average = (double)(a.b1+a.b2+a.b3+a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double obp = (double)(a.b1+a.b2+a.b3+a.hr+a.ubb+a.hbp)/a.pa;
										double slg = (double)(a.b1+2*a.b2+3*a.b3+4*a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double ops = (double)obp+slg;
										double woba = (0.72*a.ubb + 0.75*a.hbp + 0.90*a.b1 + 1.24*a.b2 + 1.56*a.b3 + 1.95*a.hr)/a.pa;
										return String.format(" 선수명 : %s / 홈런 : %d / 타율 : %.3f / 출루율 : %.3f / 장타율 : %.3f / OPS : %.3f / wOBA : %.3f /"
												+ " 타석 : %d / 1루타 : %d / 2루타 : %d / 3루타 : %d / 볼넷 : %d / 사구 : %d / 희생타 : %d",
												a.name,a.hr,average,obp,slg,ops,woba,a.pa,a.b1,a.b2,a.b3,a.ubb,a.hbp,a.sf);
									}
								}.toString();
									
								if(a.hr==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==2) {
						
						//2를 누르면 타율을 기준으로 정렬, 타율은 소숫점 3개의 double값이기에
						//비교를 위해, getAverage의 값은 타율의 1000을 곱한 값을 가져오도록 설정
						//선출력값이 높은 값으로 정렬
						
						for(int i = 10000; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
									
								if((int)a.getAverage()==i) {
									System.out.println(key+" : "+a);
								}
							
							}
						}
						System.out.println();
					}else if(menu==3) {
						
						//3을 누르면 출루율을 기준으로 정렬, 출루율은 소숫점 3개의 double값이기에
						//비교를 위해, getObp의 값은 출루율의 1000을 곱한 값을 가져오도록 설정
						//선출력값이 높은 값으로 정렬
						
						for(int i = 10000; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double average = (double)(a.b1+a.b2+a.b3+a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double obp = (double)(a.b1+a.b2+a.b3+a.hr+a.ubb+a.hbp)/a.pa;
										double slg = (double)(a.b1+2*a.b2+3*a.b3+4*a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double ops = (double)obp+slg;
										double woba = (0.72*a.ubb + 0.75*a.hbp + 0.90*a.b1 + 1.24*a.b2 + 1.56*a.b3 + 1.95*a.hr)/a.pa;
										return String.format(" 선수명 : %s / 출루율 : %.3f / 홈런 : %d / 타율 : %.3f / 장타율 : %.3f / OPS : %.3f / wOBA : %.3f /"
												+ " 타석 : %d / 1루타 : %d / 2루타 : %d / 3루타 : %d / 볼넷 : %d / 사구 : %d / 희생타 : %d",
												a.name,obp,a.hr,average,slg,ops,woba,a.pa,a.b1,a.b2,a.b3,a.ubb,a.hbp,a.sf);
									}
								}.toString();
									
								if((int)a.getObp()==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==4) {
						
						//4를 누르면 장타율을 기준으로 정렬, 장타율은 소숫점 3개의 double값이기에
						//비교를 위해, getSlg의 값은 장타율의 1000을 곱한 값을 가져오도록 설정
						//선출력값이 높은 값으로 정렬
						
						for(int i = 10000; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double average = (double)(a.b1+a.b2+a.b3+a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double obp = (double)(a.b1+a.b2+a.b3+a.hr+a.ubb+a.hbp)/a.pa;
										double slg = (double)(a.b1+2*a.b2+3*a.b3+4*a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double ops = (double)obp+slg;
										double woba = (0.72*a.ubb + 0.75*a.hbp + 0.90*a.b1 + 1.24*a.b2 + 1.56*a.b3 + 1.95*a.hr)/a.pa;
										return String.format(" 선수명 : %s / 장타율 : %.3f / 홈런 : %d / 타율 : %.3f / 출루율 : %.3f / OPS : %.3f / wOBA : %.3f /"
												+ " 타석 : %d / 1루타 : %d / 2루타 : %d / 3루타 : %d / 볼넷 : %d / 사구 : %d / 희생타 : %d",
												a.name,slg,a.hr,average,obp,ops,woba,a.pa,a.b1,a.b2,a.b3,a.ubb,a.hbp,a.sf);
									}
								}.toString();
								if((int)a.getSlg()==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==5) {
						
						//5를 누르면 OPS를 기준으로 정렬, OPS는 소숫점 3개의 double값이기에
						//비교를 위해, getOPS의 값은 OPS의 1000을 곱한 값을 가져오도록 설정
						//선출력값이 높은 값으로 정렬
						
						for(int i = 10000; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double average = (double)(a.b1+a.b2+a.b3+a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double obp = (double)(a.b1+a.b2+a.b3+a.hr+a.ubb+a.hbp)/a.pa;
										double slg = (double)(a.b1+2*a.b2+3*a.b3+4*a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double ops = (double)obp+slg;
										double woba = (0.72*a.ubb + 0.75*a.hbp + 0.90*a.b1 + 1.24*a.b2 + 1.56*a.b3 + 1.95*a.hr)/a.pa;
										return String.format(" 선수명 : %s / ops : %.3f / 홈런 : %d / 타율 : %.3f / 출루율 : %.3f / 장타율 : %.3f / wOBA : %.3f /"
												+ " 타석 : %d / 1루타 : %d / 2루타 : %d / 3루타 : %d / 볼넷 : %d / 사구 : %d / 희생타 : %d",
												a.name,ops,a.hr,average,obp,slg,woba,a.pa,a.b1,a.b2,a.b3,a.ubb,a.hbp,a.sf);
									}
								}.toString();
									
								if((int)a.getOps()==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==6) {
						
						//6을 누르면 wOBA을 기준으로 정렬, wOBA는 소숫점 3개의 double값이기에
						//비교를 위해, getWoba의 값은 wOBA의 1000을 곱한 값을 가져오도록 설정
						//선출력값이 높은 값으로 정렬
						
						for(int i = 10000; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double average = (double)(a.b1+a.b2+a.b3+a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double obp = (double)(a.b1+a.b2+a.b3+a.hr+a.ubb+a.hbp)/a.pa;
										double slg = (double)(a.b1+2*a.b2+3*a.b3+4*a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double ops = (double)obp+slg;
										double woba = (0.72*a.ubb + 0.75*a.hbp + 0.90*a.b1 + 1.24*a.b2 + 1.56*a.b3 + 1.95*a.hr)/a.pa;
										return String.format(" 선수명 : %s / woba : %.3f / 홈런 : %d / 타율 : %.3f / 출루율 : %.3f / 장타율 : %.3f / OPS : %.3f /"
												+ " 타석 : %d / 1루타 : %d / 2루타 : %d / 3루타 : %d / 볼넷 : %d / 사구 : %d / 희생타 : %d",
												a.name,woba,a.hr,average,obp,slg,ops,a.pa,a.b1,a.b2,a.b3,a.ubb,a.hbp,a.sf);
									}
								}.toString();
									
								if((int)a.getWoba()==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==7) {
						
						for(int i = 1000; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double average = (double)(a.b1+a.b2+a.b3+a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double obp = (double)(a.b1+a.b2+a.b3+a.hr+a.ubb+a.hbp)/a.pa;
										double slg = (double)(a.b1+2*a.b2+3*a.b3+4*a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double ops = (double)obp+slg;
										double woba = (0.72*a.ubb + 0.75*a.hbp + 0.90*a.b1 + 1.24*a.b2 + 1.56*a.b3 + 1.95*a.hr)/a.pa;
										return String.format(" 선수명 : %s / 타석 : %d / 홈런 : %d / 타율 : %.3f / 출루율 : %.3f / 장타율 : %.3f / OPS : %.3f /"
												+ " woba : %.3f / 1루타 : %d / 2루타 : %d / 3루타 : %d / 볼넷 : %d / 사구 : %d / 희생타 : %d",
												a.name,a.pa,a.hr,average,obp,slg,ops,woba,a.b1,a.b2,a.b3,a.ubb,a.hbp,a.sf);
									}
								}.toString();
									
								if(a.pa==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==8) {
						for(int i = 300; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double average = (double)(a.b1+a.b2+a.b3+a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double obp = (double)(a.b1+a.b2+a.b3+a.hr+a.ubb+a.hbp)/a.pa;
										double slg = (double)(a.b1+2*a.b2+3*a.b3+4*a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double ops = (double)obp+slg;
										double woba = (0.72*a.ubb + 0.75*a.hbp + 0.90*a.b1 + 1.24*a.b2 + 1.56*a.b3 + 1.95*a.hr)/a.pa;
										return String.format(" 선수명 : %s / 1루타 : %d / 홈런 : %d / 타율 : %.3f / 출루율 : %.3f / 장타율 : %.3f / OPS : %.3f /"
												+ " woba : %.3f / 타석 : %d / 2루타 : %d / 3루타 : %d / 볼넷 : %d / 사구 : %d / 희생타 : %d",
												a.name,a.b1,a.hr,average,obp,slg,ops,woba,a.pa,a.b2,a.b3,a.ubb,a.hbp,a.sf);
									}
								}.toString();
									
									
								if(a.b1==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==9) {
						
						for(int i = 300; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double average = (double)(a.b1+a.b2+a.b3+a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double obp = (double)(a.b1+a.b2+a.b3+a.hr+a.ubb+a.hbp)/a.pa;
										double slg = (double)(a.b1+2*a.b2+3*a.b3+4*a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double ops = (double)obp+slg;
										double woba = (0.72*a.ubb + 0.75*a.hbp + 0.90*a.b1 + 1.24*a.b2 + 1.56*a.b3 + 1.95*a.hr)/a.pa;
										return String.format(" 선수명 : %s / 2루타 : %d / 홈런 : %d / 타율 : %.3f / 출루율 : %.3f / 장타율 : %.3f / OPS : %.3f /"
												+ " woba : %.3f / 타석 : %d / 1루타 : %d / 3루타 : %d / 볼넷 : %d / 사구 : %d / 희생타 : %d",
												a.name,a.b2,a.hr,average,obp,slg,ops,woba,a.pa,a.b1,a.b3,a.ubb,a.hbp,a.sf);
									}
								}.toString();
									
								if(a.b2==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==10) {
						
						for(int i = 100; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double average = (double)(a.b1+a.b2+a.b3+a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double obp = (double)(a.b1+a.b2+a.b3+a.hr+a.ubb+a.hbp)/a.pa;
										double slg = (double)(a.b1+2*a.b2+3*a.b3+4*a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double ops = (double)obp+slg;
										double woba = (0.72*a.ubb + 0.75*a.hbp + 0.90*a.b1 + 1.24*a.b2 + 1.56*a.b3 + 1.95*a.hr)/a.pa;
										return String.format(" 선수명 : %s / 3루타 : %d / 홈런 : %d / 타율 : %.3f / 출루율 : %.3f / 장타율 : %.3f / OPS : %.3f /"
												+ " woba : %.3f / 타석 : %d / 1루타 : %d / 2루타 : %d / 볼넷 : %d / 사구 : %d / 희생타 : %d",
												a.name,a.b3,a.hr,average,obp,slg,ops,woba,a.pa,a.b1,a.b2,a.ubb,a.hbp,a.sf);
									}
								}.toString();
									
								if(a.b3==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==11) {
						for(int i = 300; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double average = (double)(a.b1+a.b2+a.b3+a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double obp = (double)(a.b1+a.b2+a.b3+a.hr+a.ubb+a.hbp)/a.pa;
										double slg = (double)(a.b1+2*a.b2+3*a.b3+4*a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double ops = (double)obp+slg;
										double woba = (0.72*a.ubb + 0.75*a.hbp + 0.90*a.b1 + 1.24*a.b2 + 1.56*a.b3 + 1.95*a.hr)/a.pa;
										return String.format(" 선수명 : %s / 볼넷 : %d / 홈런 : %d / 타율 : %.3f / 출루율 : %.3f / 장타율 : %.3f / OPS : %.3f /"
												+ " woba : %.3f / 타석 : %d / 1루타 : %d / 2루타 : %d / 3루타 : %d / 사구 : %d / 희생타 : %d",
												a.name,a.ubb,a.hr,average,obp,slg,ops,woba,a.pa,a.b1,a.b2,a.b3,a.hbp,a.sf);
									}
								}.toString();
									
								if(a.ubb==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==12) {
						for(int i = 100; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double average = (double)(a.b1+a.b2+a.b3+a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double obp = (double)(a.b1+a.b2+a.b3+a.hr+a.ubb+a.hbp)/a.pa;
										double slg = (double)(a.b1+2*a.b2+3*a.b3+4*a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double ops = (double)obp+slg;
										double woba = (0.72*a.ubb + 0.75*a.hbp + 0.90*a.b1 + 1.24*a.b2 + 1.56*a.b3 + 1.95*a.hr)/a.pa;
										return String.format(" 선수명 : %s / 사구 : %d / 홈런 : %d / 타율 : %.3f / 출루율 : %.3f / 장타율 : %.3f / OPS : %.3f /"
												+ " woba : %.3f / 타석 : %d / 1루타 : %d / 2루타 : %d / 3루타 : %d / 볼넷 : %d / 희생타 : %d",
												a.name,a.hbp,a.hr,average,obp,slg,ops,woba,a.pa,a.b1,a.b2,a.b3,a.ubb,a.sf);
									}
								}.toString();
									
								if(a.hbp==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else if(menu==13) {
						
						for(int i = 100; i>=0; i--) {
							for(String key : fieldermap.fmap.keySet()) {
							
								FielderData a = fieldermap.fmap.get(key);
								
								String s = new Object() {
									@Override
									public String toString() {
										double average = (double)(a.b1+a.b2+a.b3+a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double obp = (double)(a.b1+a.b2+a.b3+a.hr+a.ubb+a.hbp)/a.pa;
										double slg = (double)(a.b1+2*a.b2+3*a.b3+4*a.hr)/(a.pa-a.ubb-a.hbp-a.sf);
										double ops = (double)obp+slg;
										double woba = (0.72*a.ubb + 0.75*a.hbp + 0.90*a.b1 + 1.24*a.b2 + 1.56*a.b3 + 1.95*a.hr)/a.pa;
										return String.format(" 선수명 : %s / 희생타 : %d / 홈런 : %d / 타율 : %.3f / 출루율 : %.3f / 장타율 : %.3f / OPS : %.3f /"
												+ " woba : %.3f / 타석 : %d / 1루타 : %d / 2루타 : %d / 3루타 : %d / 볼넷 : %d / 사구 : %d",
												a.name,a.sf,a.hr,average,obp,slg,ops,woba,a.pa,a.b1,a.b2,a.b3,a.ubb,a.hbp);
									}
								}.toString();
									
								if(a.sf==i) {
									System.out.println(key+" : "+s);
								}
							
							}
						}
						System.out.println();
					}else {
						System.out.println("                   Error!!");
						System.out.println();
					}
					
				}else {//1~3값이 아니면 error출력
					System.out.println("                   Error!!");
					System.out.println();
				}
				
			}
			
			else if(menu==3) { //3을 선택할 시 선수 정보 수정
				
				System.out.println();
				System.out.println("                선수 정보 수정");
				System.out.println();
				System.out.println("         어떤 선수의 정보를 수정하시겠습니까?");
				System.out.println();
				System.out.println("               1. 투수 2. 타자");
				System.out.println();
				menu=sc.nextInt();
				if(menu==1) { // 1을 누를 시 투수 정보 수정
					System.out.println();
					System.out.println("          수정하실 투수의 정보를 입력해주세요");
					System.out.println();
					String searchPitcher = sc.next();
					if(pitchermap.pmap.keySet().stream().toList().contains(searchPitcher)==true) {
						//투수 맵의 키값과 검색한 값이 동일한 경우 검색한 키값에 투수 데이터 정보를 입력하여 기존 투수 데이터를 덮어쓴다.
						System.out.println();
						System.out.println("선수의 이름과 기록을 입력해주세요");
						System.out.println("이름 : ");
						String name = sc.next();//스캐너로 투수 데이터 클래스에 입력할 변수를 입력
						
						if( fieldermap.fmap.get(searchPitcher)==null) {
							
							
						}else if(!fieldermap.fmap.get(searchPitcher).name.equals(name) 
								&&fieldermap.fmap.get(searchPitcher)!=null) {
							
							System.out.println("동일 팀 및 배번 선수의 이름이 다릅니다.");
							System.out.println();
							continue; // while true문 처음으로 되돌아감
							
						}
						
						System.out.println("이닝 : ");
						double ip = sc.nextDouble();
						System.out.println("승 : ");
						int w = sc.nextInt();
						System.out.println("패 : ");
						int l = sc.nextInt();
						System.out.println("삼진 : ");
						int k = sc.nextInt();
						System.out.println("볼넷 : ");
						int ubb = sc.nextInt();
						System.out.println("사구 : ");
						int hbp = sc.nextInt();
						System.out.println("홈런 : ");
						int hr = sc.nextInt();
						System.out.println("실점 : ");
						int r = sc.nextInt();
						System.out.println("자책점 : ");
						int rr = sc.nextInt();
						
						PitcherData pitcherdata = new PitcherData(name, ubb, hbp, hr, ip, k, r, rr, w, l); // 스캐너로 입력받은 변수를 투수 클래스에 변수로 입력하여 객체 생성
						
						pitchermap.pmap.put(searchPitcher, pitcherdata); // 검색값을 key값으로, 변수를 입력해 만든 투수 데이터 클래스의 객체를 value값으로 입력
						
						FileWriter fwrite = new FileWriter(file); //writer를 사용해 백업용 txt파일에 while true문 안에 누적된 타자 데이터를 문자열로 입력
						
						for(String key : pitchermap.pmap.keySet()) {
							
							PitcherData a = pitchermap.pmap.get(key);
								
							fwrite.write(key+" / "+a+"\n");
							}
						
						
						fwrite.flush();
						fwrite.close();//writer 종료
						
						
						
						System.out.println();
						System.out.println("     수정 완료된 투수 정보 확인");
						System.out.println();
						System.out.println(searchPitcher+" "+pitchermap.pmap.get(searchPitcher)); // 수정한 투수의 정보를 출력
						
					}else {
						System.out.println("                  투수 정보 없음"); //검색한 투수의 정보가 투수 맵에 key값으로 존재하지 않는 경우 투수 정보 없음 출력
						System.out.println();
					}
				}else if(menu==2) { // 2를 누를 시 타자 정보 수정
					System.out.println();
					System.out.println("          수정하실 타자의 정보를 입력해주세요");
					System.out.println();
					String searchHitter = sc.next();
					if(fieldermap.fmap.keySet().stream().toList().contains(searchHitter)==true) {
						//입력 받은 검색값이 타자 map 내의 key값 중 일치하는 경우 해당 key값에 야수 데이터를 덮어쓴다.
						System.out.println();
						System.out.println("선수의 이름과 기록을 입력해주세요");
						System.out.println("이름 : ");
						String name = sc.next(); //스캐너로 야수 데이터 클래스에 들어갈 변수값을 입력 및 정의
						
						if(pitchermap.pmap.get(searchHitter)==null) {
							
						}else if(!pitchermap.pmap.get(searchHitter).name.equals(name) //동일 팀 동일 배번인데 등록된 타자가 동명 선수가 아닐 경우 
								&&pitchermap.pmap.get(searchHitter)!=null) {
							System.out.println("동일 팀 및 배번 선수의 이름이 다릅니다.");
							continue;
						}
						
						System.out.println("타석 : ");
						int pa = sc.nextInt();
						System.out.println("안타 : ");
						int b1 = sc.nextInt();
						System.out.println("2루타 : ");
						int b2 = sc.nextInt();
						System.out.println("3루타 : ");
						int b3 = sc.nextInt();
						System.out.println("홈런 : ");
						int hr = sc.nextInt();
						System.out.println("희생타 : ");
						int sf = sc.nextInt();
						System.out.println("볼넷");
						int ubb = sc.nextInt();
						System.out.println("몸에 맞는 공");
						int hbp = sc.nextInt();
						
						FielderData fielderData = new FielderData(name, ubb, hbp, b1, b2, b3, hr, pa, sf); //스캐너로 입력받은 변수값을 야수 데이터 클래스에 집어넣고 객체 생성
						
						fieldermap.fmap.put(searchHitter, fielderData); // 야수 map에 검색값을 key값으로 야수 데이터 클래스 객체를 value값으로 저장
						
						FileWriter fwrite = new FileWriter(file2); //while문에 누적된 야수 map 정보를 writer를 이용하여 txt 파일에 문자열로 출력
						
						
						for(String key : fieldermap.fmap.keySet()) {
						
						FielderData a = fieldermap.fmap.get(key);
							
						fwrite.write(key+" / "+a+"\n");
						}
					
					fwrite.flush();
					fwrite.close(); // writer 종료
						
						System.out.println();
						System.out.println("     수정 완료된 타자 정보 확인");
						System.out.println();
						System.out.println(searchHitter+" "+fieldermap.fmap.get(searchHitter));
						
					}else { // 검색값과 타자 map 내의 키값이 일치하지 않을 시 타자 정보 없음 출력
						System.out.println("                  타자 정보 없음");
						System.out.println();
					}
					
				}else { // 투수정보 수정, 타자 정보 수정이 할당된 1,2가 아닐 시 error 출력
					System.out.println("                   Error!!");
					System.out.println();
				}
				
			}else if(menu==4) { // 메인 메뉴에서 4를 누를 시 선수 정보 삭제로 넘어온다.
				
				System.out.println();
				System.out.println("                선수 정보 삭제");
				System.out.println();
				System.out.println("         어떤 선수의 정보를 삭제하시겠습니까?");
				System.out.println();
				System.out.println("               1. 투수 2. 타자");
				menu=sc.nextInt();
				if(menu==1) { //1을 입력시 투수 정보 삭제
					System.out.println();
					System.out.println("        삭제할 투수의 정보를 입력해주세요");
					System.out.println();
					String searchPitcher = sc.next();
					if(pitchermap.pmap.keySet().stream().toList().contains(searchPitcher)==true) {
						//검색한 값과 투수 맵의 키값이 일치할 경우
						System.out.println();
						System.out.println("삭제되었습니다");//삭제되었습니다를 출력하고
						System.out.println();
						pitchermap.pmap.remove(searchPitcher); // remove 메서드를 이용하여 map에서 검색한 값을 삭제
						
						FileWriter fwrite = new FileWriter(file); // 이후 writer를 이용하여 txt 파일에 while문 안에 누적된 투수 데이터를 문자열로 저장
						
						for(String key : pitchermap.pmap.keySet()) {
							
							PitcherData a = pitchermap.pmap.get(key);
								
							fwrite.write(key+" / "+a+"\n");
							}
						
						
						fwrite.flush(); 
						fwrite.close(); //writer 종료
						
					}else {//검색한 값과 투수 맵의 키값이 일치하지 않은 경우 투수 정보 없음 출력
						System.out.println("               투수 정보 없음");
						System.out.println();
					}
				}else if(menu==2) {//2를 입력시 타자정보 삭제로 넘어온다.
					System.out.println();
					System.out.println("         삭제할 타자의 정보를 입력해주세요");
					System.out.println();
					String searchHitter = sc.next();
					if(fieldermap.fmap.keySet().stream().toList().contains(searchHitter)==true) {
						
						//타자 맵의 key값이 검색값과 일치할 경우
						
						System.out.println();
						System.out.println("삭제되었습니다");//삭제되었습니다를 출력하고
						System.out.println();
						fieldermap.fmap.remove(searchHitter); //remove 메서드를 통해 검색한 key값의 데이터를 삭제
						
						FileWriter fwrite = new FileWriter(file2);  // 이후 writer를 이용하여 txt 파일에 while문 안에 누적된 투수 데이터를 문자열로 저장
						
						
						for(String key : fieldermap.fmap.keySet()) {
						
						FielderData a = fieldermap.fmap.get(key);
							
						fwrite.write(key+" / "+a+"\n");
						}
					
					fwrite.flush();
					fwrite.close();//writer 종료
						
					}else {//검색한 값과 타자 맵의 키값이 일치하지 않은 경우 타자 정보 없음 출력
						System.out.println("               타자 정보 없음");
						System.out.println();
					}
				}else { // 투수 정보 삭제와 타자 정보 삭제의 1,2가 아닌 다른 값을 입력한 경우 error 출력
					System.out.println("                   Error!!");
					System.out.println();
				}
				
			}else { //메인 메뉴에서 1,2,3,4가 아닌 다른 값을 출력했을 경우 error를 출력
				System.out.println("                   Error!!");
				System.out.println();
			}
			
			}catch(InputMismatchException e) { //menu 입력값이 정수가 아닐 시 생기는 InputException을 해결하기 위한 예외처리
				System.out.println("                   Error!!");
				System.out.println();
				break; // 무한 반복을 막기 위한 break
			}
		}
		
		
	}
	
}
