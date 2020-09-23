package lotto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class NumList {
	int[][] nums;
	int count=0;
	int lottoLength=6;
	
	public static void menu3(NumList list) throws IOException {
		FileInputStream fis2 = new FileInputStream("res/lottoNumbers.txt");
		Scanner scan2 = new Scanner(fis2);
		FileInputStream fis = new FileInputStream("res/lottoNumbers.txt");
		Scanner scan = new Scanner(fis);
		
		list.count=0;
		while(scan.hasNextLine()) {
			scan.nextLine();
			list.count++;
		}
		scan.close();
		fis.close();
				
		list.nums = new int[list.count][list.lottoLength];
	    int index=0;
	    while(scan2.hasNextLine()) {
	    	String[] tokens = scan2.nextLine().split(" ");
	    	for(int i=0;i<list.lottoLength;i++) {
	    		list.nums[index][i] = Integer.parseInt(tokens[i]);
	    	}
	    	index++;
	    }
	    scan2.close();
	    fis2.close();
	}

	public static void menu2(Scanner scan,NumList list) throws IOException {
		
		list.count = 0;
	    System.out.println("번호를 입력하세요(예 6 7 10 20 30 40)\n(단, 그만입력하려면 -1을 입력하세요.)");
	    FileOutputStream fos = new FileOutputStream("res/lottoString.txt");
	    PrintStream ps = new PrintStream(fos);
	    
	    L1:
	    L2: while(true) {
	    	
	    	String input = scan.nextLine();
	    	String[] tokens = input.split(" ");
	    	int count=0;
	    	String inputresult="";
	    	
	    	//유효성 검사
	    	if(tokens.length != list.lottoLength) {
	    		for(String s : tokens) {
	    			if(s.equals("-1"))
			    		break L1;
	    			else if(s.equals("")) {
		    			continue;
		    		}
	    			
	    			count++;
	    		}
	    		
	    		if(count != list.lottoLength) {
	    			System.out.println("로또 번호는 6개의 숫자만 입력 가능합니다.");
	    			continue L2;
	    		}
	    	}
	    	
	    	count=0;
	    	
	    	for(String s : tokens) {
	    		
	    		if(s.equals("") && count == 0) {
	    			System.out.println("로또 번호에 0이 포함될 수 없습니다. 다시 입력해주세요.");
	    			continue L2;
	    		}
	    		
	    		if(s.equals("")) {
	   				continue;
	   			}
	   			
	   			if(s.equals("-1"))
    				break L1;
	    		else if(!isStringInt(s)) {
	    			System.out.println("숫자만 입력 가능합니다.");
	    			continue L2;
	    		}
	    		else if(Integer.parseInt(s) < -1 || Integer.parseInt(s) > 45) {
	    			System.out.println("1~45 사이의 숫자만 입력해주세요.");
	    			continue L2;
	    		}
	   			
	    		count++;
	    		inputresult += (s+" ");
	    	}
	    	
	    	
	    	ps.print(inputresult+"\n");
	    	list.count++;
	    }
	    ps.close();
	    fos.close();
	    
	    FileInputStream fis = new FileInputStream("res/lottoString.txt");
	    Scanner scan2 = new Scanner(fis);
	    list.nums = new int[list.count][list.lottoLength];
	    int index=0;
	    while(scan2.hasNextLine()) {
	    	String[] tokens = scan2.nextLine().split(" ");
	    	for(int i=0;i<tokens.length;i++) {
	    		list.nums[index][i] = Integer.parseInt(tokens[i]);
	    	}
	    	index++;
	    }
	    fis.close();
	    scan2.close();
	}
	
	public static boolean isStringInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}

	public static void template2() {
		System.out.println("┌──────────────────────────┐");
		System.out.println("│    2.  Lotto 번호 수동생성        │");
		System.out.println("└──────────────────────────┘");
	}

	public static void menu1(Scanner scan, NumList list) throws IOException {
		
	    System.out.print("생성할 개수 : ");
	    list.count = scan.nextInt();
	}
	
	public static void template1() {
		System.out.println("┌──────────────────────────┐");
		System.out.println("│    1.  Lotto 번호 자동생성        │");
		System.out.println("└──────────────────────────┘");
	}

	public static void setLottoNum(NumList list) throws IOException {

	    //로또 번호 생성
	    list.nums = new int[list.count][list.lottoLength];
	    
	    for(int i=0;i<list.count;i++) {
	    	for(int j=0;j<list.lottoLength;j++) {
	    		while(true) {
	    			int num = (int) (Math.random()*45+1);
			    	int check =0;
			    	for(int k=0;k<list.lottoLength;k++) {
			    		if(list.nums[i][k] == num)
			    			check++;
			    	}
			    	if(check ==0) {
			    		list.nums[i][j] = num;
			    		break;
			    	}
	    		}
		    }
	    }
	}
	
	public static void sort(NumList list){
		for(int i=0;i<list.count;i++) {
	    	for(int j=0;j<list.lottoLength-1;j++) {
	    		for(int k=0;k<list.lottoLength-1-j;k++) {
	    			if(list.nums[i][k] > list.nums[i][k+1]) {
	    				int temp = list.nums[i][k];
	    				list.nums[i][k] = list.nums[i][k+1];
	    				list.nums[i][k+1] = temp;
	    			}
	    		}
	    	}
	    }
	}

	public static void printLotto(Scanner scan, NumList list, int select) throws IOException {
		
	    for(int i=0;i<list.count;i++) {
	    	for(int j=0;j<list.lottoLength;j++) {
	    		if(j==0)
	    			System.out.printf("   %d >> ",i+1);
	    		System.out.printf("%3d",list.nums[i][j]);
	    	}System.out.println();
	    }
	    if(select == 1 || select == 2) {
	    	L1:
	    	while(true) {
	    		int select2 = printSubMenu(scan, select);
			    
			    switch(select2) {
			    case 1:
			    	store(list);
			    	break L1;
			    case 2:
			    	break L1;
			    default :
			    	System.out.println("없는 메뉴입니다.");
			    	break;
			    }
	    	}
	    	
	    }
	    else {
	    	L1:
	    	while(true) {
	    		int select2 = printSubMenu(scan,select);
		    	switch(select2) {
			    case 1:
			    	System.out.println("메인메뉴로 이동합니다.");
			    	break L1;
			    default :
			    	System.out.println("없는 메뉴 입니다.");
			    	break;
			    }
	    	}
	    }
	}

	public static void store(NumList list) throws IOException {
		FileOutputStream fos = new FileOutputStream("res/lottoNumbers.txt",true);
		PrintStream ps = new PrintStream(fos);
		
		for(int i=0;i<list.count;i++) {
			String line="";
			for(int j=0;j<list.lottoLength;j++) {
				line += Integer.toString(list.nums[i][j]);
				line += " ";
			}
			ps.print(line+"\n");
		}
		
		ps.close();
		fos.close();
	}

	public static int printSubMenu(Scanner scan, int select) {
		int select2;
		if(select == 1 || select == 2) {
			System.out.println("\t\t1. 저장하기");
		    System.out.println("\t\t2. 상위메뉴로 가기");
		    
		    select2 = scan.nextInt();
		}
		else {
			System.out.println();
			System.out.println("\t\t1. 상위메뉴로 가기");
			select2 = scan.nextInt();
		}
		
	    return select2;
	}

	public static void printMenu() {
		System.out.println("┌────────────────────────┐");
        System.out.println("│      Lotto 관리 프로그램        │");
        System.out.println("└────────────────────────┘");
	    
	    System.out.println("\t1. 번호 자동 생성");
	    System.out.println("\t2. 번호 수동 생성");
	    System.out.println("\t3. 내 로또 번호 보기");
	    System.out.println("\t4. 종료");
	    
	}
}
