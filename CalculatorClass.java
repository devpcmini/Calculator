import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalculatorClass extends JFrame {
    static JLabel label; // 숫자 표시 레이블
    static JLabel info; // 계산 과정 표시 레이블
    double result = 0.0D; // 결과값
    String math = ""; // 수학 연산 기호 저장
    double num = 0.0D; // 현재 입력된 숫자 저장

    // 생성자
    public CalculatorClass() {
        setTitle("박창민의 계산기"); // 프레임 제목 설정
        setDefaultCloseOperation(3); // 닫기 동작 설정
        Container calculatorContainer = getContentPane(); // 계산기 컨테이너
        calculatorContainer.setLayout(new BorderLayout(5, 5)); // 레이아웃 설정
        calculatorContainer.setBackground(Color.LIGHT_GRAY); // 배경색 설정
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image img = toolkit.getImage(getClass().getClassLoader().getResource("img/sg.jpg"));
        setIconImage(img); // 아이콘 설정
        CalcLabelPanel calcLabel = new CalcLabelPanel(); // 숫자 및 계산 과정 표시 패널
        calculatorContainer.add(calcLabel, "East"); // 패널 추가
        ResultLabel resultLabel = new ResultLabel(); // 결과 표시 패널
        calculatorContainer.add(resultLabel, "South"); // 패널 추가
        setSize(350, 600); // 프레임 크기 설정
        setVisible(true); // 프레임 표시
    }

    // 이미지 아이콘 버튼 생성
    protected JButton createImageIcon(String text, String path) {
        ImageIcon tempImg = new ImageIcon(getClass().getClassLoader().getResource(path));
        if (tempImg != null) {
            Image img = tempImg.getImage();
            Image updateImg = img.getScaledInstance(40, 40, 4);
            ImageIcon updateIcon = new ImageIcon(updateImg);
            JButton result = new JButton(updateIcon);
            result.setName(text);
            return result;
        } else {
            return null;
        }
    }
    // 백스페이스 설정
    private void setBackSpace(String bs) {
        label.setText(bs);
    }

    // 백스페이스 가져오기
    private String getBackSpace() {
        return label.getText();
    }

    // 메인 메소드
    public static void main(String[] args) {
        new CalculatorClass(); // 계산기 인스턴스 생성
    }

    // 계산을 수행하는 액션 이벤트 리스너 클래스
    public class Result implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton)e.getSource(); // 이벤트가 발생한 버튼 가져오기
            String labeltext = label.getText(); // 결과 레이블의 텍스트 가져오기
            String text = b.getName(); // 버튼의 이름 가져오기
            String newtext = labeltext + text; // 새로운 텍스트 생성 (현재 텍스트에 버튼의 이름 추가)
            int n = newtext.length(); // 새로운 텍스트의 길이 저장

            // '^2' 버튼이 아닌 경우에는 현재 입력된 숫자 업데이트
            if (text != "＾2") {
                num = Double.parseDouble(label.getText().substring(0, n - 1));
            }
            // 수학 연산 수행
            if (math == "+") {
                result += num;
                math = "";
            } else if (math == "-") {
                result -= num;
                math = "";
            } else if (math == "×") {
                result *= num;
                math = "";
            } else if (math == "÷") {
                result /= num;
                math = "";
            }
            // 수학 연산 기호 업데이트
            if (math == "") {
                math = b.getName();
            }
            // 정보 레이블 및 결과 업데이트
            if (info.getText() == "" && text != "＾2" && text != "=") {
                info.setText(newtext);
                result = num;
                label.setText("0");
            } else if (info.getText() != "" && text != "＾2" && text != "=") {
                result = (double)Math.round(result * 1.0E9D) / 1.0E9D;
                if (result % 1.0D == 0.0D) {
                    info.setText((int)result + text);
                    label.setText("0");
                } else {
                    info.setText(result + text);
                    label.setText("0");
                }
            }
            // 제곱 계산
            if (text == "＾2") {
                num = Double.parseDouble(label.getText().substring(0, n - 2));
                math = "";
                if (info.getText() == "") {
                    result = (double)Math.round(Math.pow(num, 2.0D) * 1.0E9D) / 1.0E9D;
                    if (result % 1.0D == 0.0D) {
                        info.setText("pow(" + (int) num + ")");
                        label.setText(String.valueOf((int)result));
                    } else {
                        info.setText("pow(" + num + ")");
                        label.setText(String.valueOf(result));
                    }
                } else if (result % 1.0D == 0.0D) {
                    info.setText("pow(" + (int) result + ")");
                    result = (double)Math.round(Math.pow(result, 2.0D) * 1.0E9D) / 1.0E9D;
                    label.setText(String.valueOf((int)result));
                } else {
                    info.setText("pow(" + result + ")");
                    result = (double)Math.round(Math.pow(result, 2.0D) * 1.0E9D) / 1.0E9D;
                    label.setText(String.valueOf(result));
                }
            }
            // 제곱근 계산
            if (text == "√") {
                math = "";
                if (info.getText() == "") {
                    result = (double)Math.round(Math.sqrt(num) * 1.0E9D) / 1.0E9D;
                    if (result % 1.0D == 0.0D) {
                        info.setText("sqrt(" + (int) num + ")");
                        label.setText(String.valueOf((int)result));
                    } else {
                        info.setText("sqrt(" + num + ")");
                        label.setText(String.valueOf(result));
                    }
                } else if (Math.sqrt(result) % 1.0D == 0.0D) {
                    info.setText("sqrt(" + (int) result + ")");
                    result = (double)Math.round(Math.sqrt(result) * 1.0E9D) / 1.0E9D;
                    label.setText(String.valueOf((int)result));
                } else {
                    info.setText("sqrt(" + result + ")");
                    result = (double)Math.round(Math.sqrt(result) * 1.0E9D) / 1.0E9D;
                    label.setText(String.valueOf(result));
                }
            }
            // 결과 계산
            if (text == "=") {
                math = "";
                if (info.getText() != "" && label.getText() != "" && !info.getText().contains(text)) {
                    if (result % 1.0D == 0.0D) {
                        info.setText(info.getText() + label.getText());
                        label.setText(String.valueOf((int)result));
                    } else {
                        info.setText(info.getText() + label.getText());
                        label.setText(String.valueOf(result));
                    }
                }
            }

        }
    }

    // 계산 결과를 표시하는 패널 클래스
    class ResultLabel extends JPanel {
        public ResultLabel() {
            JButton[] bt = new JButton[20];
            setLayout(new GridLayout(5, 4, 5, 5));
            setBackground(Color.DARK_GRAY);
            // 각 버튼 생성 및 설정
            bt[0] = createImageIcon("×", "img/×.png");
            bt[1] = createImageIcon("÷", "img/÷.png");
            bt[2] = createImageIcon("AC", "img/AC.png");
            bt[3] = createImageIcon("C", "img/cancel.png");
            bt[4] = createImageIcon("7", "img/7.png");
            bt[5] = createImageIcon("8", "img/8.png");
            bt[6] = createImageIcon("9", "img/9.png");
            bt[7] = createImageIcon("√", "img/√.png");
            bt[8] = createImageIcon("4", "img/4.png");
            bt[9] = createImageIcon("5", "img/5.png");
            bt[10] = createImageIcon("6", "img/6.png");
            bt[11] = createImageIcon("-", "img/-.png");
            bt[12] = createImageIcon("1", "img/1.png");
            bt[13] = createImageIcon("2", "img/2.png");
            bt[14] = createImageIcon("3", "img/3.png");
            bt[15] = createImageIcon("+", "img/+.png");
            bt[16] = createImageIcon("＾2", "img/＾2.png");
            bt[17] = createImageIcon("0", "img/0.png");
            bt[18] = createImageIcon(".", "img/..png");
            bt[19] = createImageIcon("=", "img/=.png");

            // 버튼 이벤트 처리
            for(int i = 0; i <= 19; ++i) {
                bt[i].setPreferredSize(new Dimension(0, 65));
                bt[i].setFont(new Font("맑은 고딕", 0, 20));
                bt[i].setForeground(Color.BLACK);
                bt[i].setBackground(new Color(240, 240, 240));
                if (3 < i && i < 15 && i % 4 != 3) { // 숫자 및 연산자 버튼 이벤트 처리
                    bt[i].addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JButton b = (JButton)e.getSource();
                            if (label.getText() == "0") {
                                label.setText("");
                            }

                            String labeltext = label.getText();
                            String text = b.getName();
                            String newtext = labeltext + text;
                            int n = newtext.length();
                            if (n <= 10) {
                                label.setText(newtext);
                            }

                            if (info.getText().contains("=")) {
                                info.setText("");
                                label.setText(b.getName());
                                result = 0.0D;
                                num = 0.0D;
                            }

                        }
                    });
                } else if (i == 17) { // 숫자 '0' 버튼 이벤트 처리
                    bt[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JButton b = (JButton)e.getSource();
                            String labeltext = label.getText();
                            String text = b.getName();
                            String newtext = labeltext + text;
                            if (labeltext == "0") {
                                label.setText("0");
                            } else {
                                label.setText(newtext);
                            }

                        }
                    });
                } else if (i == 19) { // '=' 버튼 이벤트 처리
                    bt[i].setBackground(new Color(78, 197, 255));
                    bt[i].addActionListener(new Result());
                } else if (i % 4 == 3 || i < 3 || 15 < i) { // AC, C, 백스페이스, '.' 버튼 이벤트 처리
                    bt[i].setBackground(new Color(218, 218, 218));
                    if (i == 2) {
                        bt[i].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int n = label.getText().length();
                                if (n >= 0) {
                                    label.setText("0");
                                    info.setText("");
                                }
                                result = 0.0D;
                            }
                        });
                    } else if (i == 3) {
                        bt[i].addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int n = label.getText().length();
                                if (n > 0) {
                                    setBackSpace(getBackSpace().substring(0, getBackSpace().length() - 1));
                                }

                                if (label.getText() == "") {
                                    label.setText("0");
                                }

                            }
                        });
                    } else if (i == 18) {
                        bt[i].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JButton b = (JButton)e.getSource();
                                String labeltext = label.getText();
                                String text = b.getName();
                                String newtext = labeltext + text;
                                int n = newtext.length();
                                if (!label.getText().contains(b.getName()) && n < 10) {
                                    label.setText(newtext);
                                }

                            }
                        });
                    } else {
                        bt[i].addActionListener(new Result());
                    }
                }

                add(bt[i]);
            }

        }
    }

    // 계산 결과와 정보를 표시하는 패널
    class CalcLabelPanel extends JPanel {
        public CalcLabelPanel() {
            setLayout(new GridLayout(3, 1));
            setBackground(Color.LIGHT_GRAY);
            info = new JLabel("");
            label = new JLabel("0");
            info.setFont(new Font("맑은 고딕", 0, 40));
            info.setForeground(Color.BLACK);
            info.setHorizontalAlignment(JLabel.RIGHT);
            label.setFont(new Font("맑은 고딕", 1, 55));
            label.setForeground(Color.BLACK);
            label.setHorizontalAlignment(JLabel.RIGHT);
            add(info);
            add(label);
        }
    }
}