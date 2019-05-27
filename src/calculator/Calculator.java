package calculator;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Calculator implements ActionListener{
	//実行
	public static void main(String[] args) {
		new Calculator();
	}

	//表示用フィールド
	private JLabel label;

	/**
	 * ウインドウ表示内容を記載
	 */
	public Calculator() {

		JFrame frame = new JFrame( Contants.windowName );

		GridLayout GL1 = new GridLayout(5,1);
		frame.getContentPane().setLayout( GL1 );

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();

		//1段目レイアウト調整部分
//		GridBagLayout gbl = new GridBagLayout();
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.gridx = 0;
//		gbc.gridy = 0;
//		gbc.gridwidth = 3;
//		gbc.gridheight = 1;

		label  = new JLabel();

//		gbl.setConstraints( label , gbc );
//		p1.setLayout( gbl );
//		p1.add( new JButton( "AC" ));

//		p1.add(label,BorderLayout.LINE_END);
//		p1.getContentPane().add(p1,BorderLayout.LINE_END);

		//1段目ラベル部分
		p1.setLayout(new GridLayout(1,2));
		p1.add(label,BorderLayout.LINE_START);

		//1段目ACボタン部分
		JButton AC = new JButton(Contants.B1d);
		AC.addActionListener(this);
		p1.add(AC);

		GridLayout GL2 = new GridLayout(1,4);
		p2.setLayout( GL2 );
		p3.setLayout( GL2 );
		p4.setLayout( GL2 );
		p5.setLayout( GL2 );

		String ButtonName[][] = {
			{Contants.B2a,Contants.B2b,Contants.B2c,Contants.B2d},
			{Contants.B3a,Contants.B3b,Contants.B3c,Contants.B3d},
			{Contants.B4a,Contants.B4b,Contants.B4c,Contants.B4d},
			{Contants.B5a,Contants.B5b,Contants.B5c,Contants.B5d},
		};

		JPanel[] Panels = {p2,p3,p4,p5};

		int i = 0;
		for (String[] ButtonLine : ButtonName) {

			JPanel P = Panels[i];
			i++;
			for(String Button : ButtonLine) {
				JButton button = new JButton(Button);
				button.addActionListener(this);
				P.add(button);
			}
		}

		frame.add(p1,BorderLayout.LINE_END);
		frame.add(p2);
		frame.add(p3);
		frame.add(p4);
		frame.add(p5);

		frame.setBounds( 0 , 0 , 400 , 400 );
		frame.setVisible( true );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}

	@Override
	//入力された数字によって処理を分岐
	public void actionPerformed(ActionEvent e) {
		String enterdValue = e.getActionCommand();

		if ( Contants.isNumber.indexOf(enterdValue) != -1 ) {
			addNumber(enterdValue);
		} else if ( Contants.isOperator.indexOf(enterdValue) != -1 ) {
			addSecondNumber(enterdValue);
		} else if ( Contants.isEqual.equals(enterdValue) ) {
			if( !"".contentEquals(Operator) ) {
				executeCalculation();
			}
		} else if ( Contants.isAC.equals(enterdValue) ) {
			deleteNumber();
		}

		label.setText(FirstNum + Operator + OutputNum);
	}

	//表示用フィールド
	String OutputNum = "";

	//計算用フィールド
	String FirstNum = "";
	String SecondNum = "";
	String Operator = "";
	boolean executeFrag = false;

	//文字列追加処理
	private void addNumber(String S) {
		//計算実行直後の結果表示画面から数字入力された場合は表示中の数値とフラグを初期化
		if (executeFrag) {
			OutputNum = "";
			executeFrag = false;
		}

		OutputNum += S;

		//表示文字列が"."のみの場合、"0."に補正
		if ( ".".equals(OutputNum)) {
			addNumber("0");
		}
	}

	//計算実行処理
	private void executeCalculation() {
		//現在の表示値を計算用フィールドに代入
		SecondNum = OutputNum;

		//計算用フィールドの値をdouble型に変形
		double firstNum  = Double.parseDouble(FirstNum);
		double secondNum = Double.parseDouble(SecondNum);
		double resultNum = 0;

		//演算子毎に分岐し、計算処理を実施
		if( Contants.isPlus.equals(Operator)) {
			resultNum = firstNum + secondNum;
		}else if( Contants.isMinus.equals(Operator)) {
			resultNum = firstNum - secondNum;
		}else if( Contants.isMultiplied.equals(Operator)) {
			resultNum = firstNum * secondNum;
		}else if( Contants.isDivided.equals(Operator)) {
			resultNum = firstNum / secondNum;
		}

		//計算結果を表示用フィールドに代入
		OutputNum = String.valueOf(resultNum);

		//各種フィールドを初期化
		FirstNum = "";
		SecondNum = "";
		Operator = "";

		//fragを設定
		executeFrag = true;
	}

	//計算前入力枠変更処理
	private void addSecondNumber(String S) {
		//既に演算子を入力されていたかで分岐
		if(Operator == "") {
			//入力された演算子を計算用フィールドに代入
			FirstNum = OutputNum;
			OutputNum = "";
			//入力された演算子を計算用フィールドに代入
			Operator = S;
		}else {
			//"＝"と同等の処理を実施後、演算子を計算用フィールドに代入
			executeCalculation();
			FirstNum = OutputNum;
			OutputNum = "";
			Operator = S;
		}
	}

	//数値初期化処理
	private void deleteNumber() {
		OutputNum = "";
		FirstNum = "";
		SecondNum = "";
		Operator = "";
		executeFrag = false;
	}

}
