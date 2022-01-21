package com.example.admin.calculator_swenson;

import android.os.StrictMode;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // TTTT is "times ten to the"
    public enum Op {
        NONE(0), ADD(1), SUBTRACT(2), MULTIPLY(3), DIVIDE(4), TTTT(5), POW(6);

        private final int value;
        private Op(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static Op fromValue(int value) {
            switch (value) {
                case 1:
                    return ADD;
                case 2:
                    return SUBTRACT;
                case 3:
                    return MULTIPLY;
                case 4:
                    return DIVIDE;
                case 5:
                    return TTTT;
                case 6:
                    return POW;
                case 0:
                default:
                    return NONE;
            }
        }
    }

    private Double value = null;
    private Op op = Op.NONE;

    private boolean validArg(String s) {
        try {
            Double.parseDouble(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void apply(Op next, double value1) {
        if (this.op.equals(Op.NONE)) {
            this.op = next;
            this.value = value1;
        }
        else {
            switch (this.op) {
                case ADD:
                    value = value + value1;
                    break;
                case SUBTRACT:
                    value = value - value1;
                    break;
                case MULTIPLY:
                    value = value * value1;
                    break;
                case DIVIDE:
                    value = value / value1;
                    break;
                case POW:
                    value = Math.pow(value, value1);
                    break;
                case TTTT:
                    value = value * Math.pow(10, value1);
                    break;
                case NONE:
                    value = value1;
                    break;
            }
            this.op = next;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvValue = (TextView) this.findViewById(R.id.tvValue);

        if (savedInstanceState != null) {
            String s = savedInstanceState.getString("value");
            if (s == null)
                value = null;
            else
                value = Double.parseDouble(s);
            tvValue.setText(savedInstanceState.getString("etValue"));
            op = Op.fromValue(savedInstanceState.getInt("op"));
        }

        Button[] pinpad = new Button[10];
        pinpad[0] = (Button) this.findViewById(R.id.button0);
        pinpad[1] = (Button) this.findViewById(R.id.button1);
        pinpad[2] = (Button) this.findViewById(R.id.button2);
        pinpad[3] = (Button) this.findViewById(R.id.button3);
        pinpad[4] = (Button) this.findViewById(R.id.button4);
        pinpad[5] = (Button) this.findViewById(R.id.button5);
        pinpad[6] = (Button) this.findViewById(R.id.button6);
        pinpad[7] = (Button) this.findViewById(R.id.button7);
        pinpad[8] = (Button) this.findViewById(R.id.button8);
        pinpad[9] = (Button) this.findViewById(R.id.button9);
        int c;
        for (c = 0; c < pinpad.length; c++) {
            final int cf = c;
            pinpad[c].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String s = tvValue.getText().toString();
                    tvValue.setText(s + cf);
                }
            });
        }

        Button buttonPoint = (Button) this.findViewById(R.id.buttonPoint);
        buttonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = tvValue.getText().toString();
                if (!s.contains("."))
                    tvValue.setText(s + ".");
            }
        });

        final Button buttonMinus = (Button) this.findViewById(R.id.buttonMinus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = tvValue.getText().toString();
                if (s.equals(""))
                    tvValue.setText("-");
                else if (validArg(s)) {
                    double b = Double.parseDouble(s);
                    apply(Op.SUBTRACT, b);
                    tvValue.setText("");
                }
            }
        });

        final Button buttonPlus = (Button) this.findViewById(R.id.buttonPlus);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = tvValue.getText().toString();
                if (validArg(s)) {
                    double b = Double.parseDouble(s);
                    apply(Op.ADD, b);
                    tvValue.setText("");
                }
            }
        });

        final Button buttonMult = (Button) this.findViewById(R.id.buttonMult);
        buttonMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = tvValue.getText().toString();
                if (validArg(s)) {
                    double b = Double.parseDouble(s);
                    apply(Op.MULTIPLY, b);
                    tvValue.setText("");
                }
            }
        });

        final Button buttonDiv = (Button) this.findViewById(R.id.buttonDiv);
        buttonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = tvValue.getText().toString();
                if (validArg(s)) {
                    double b = Double.parseDouble(s);
                    apply(Op.DIVIDE, b);
                    tvValue.setText("");
                }
            }
        });

        final Button buttonTTTT = (Button) this.findViewById(R.id.buttonTTTT);
        if (buttonTTTT != null) {
            buttonTTTT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String s = tvValue.getText().toString();
                    if (validArg(s)) {
                        double b = Double.parseDouble(s);
                        apply(Op.TTTT, b);
                        tvValue.setText("");
                    }
                }
            });
        }

        final Button buttonPow = (Button) this.findViewById(R.id.buttonPow);
        buttonPow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = tvValue.getText().toString();
                if (validArg(s)) {
                    double b = Double.parseDouble(s);
                    apply(Op.POW, b);
                    tvValue.setText("");
                }
            }
        });

        final Button buttonEquals = (Button) this.findViewById(R.id.buttonEquals);
        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = tvValue.getText().toString();
                if (validArg(s)) {
                    double b = Double.parseDouble(s);
                    apply(Op.NONE, b);
                    tvValue.setText("" + value);
                }
            }
        });

        final Button buttonBsp = (Button) this.findViewById(R.id.buttonBsp);
        buttonBsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = tvValue.getText().toString();
                if (s.length() > 0)
                    tvValue.setText(s.substring(0, s.length() - 1));
            }
        });

        final Button buttonC = (Button) this.findViewById(R.id.buttonC);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = tvValue.getText().toString();
                if (s.isEmpty())
                    value = null;
                tvValue.setText("");
            }
        });

        final Button buttonSqrt = (Button) this.findViewById(R.id.buttonSqrt);
        buttonSqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = tvValue.getText().toString();
                if (validArg(s)) {
                    apply(Op.NONE, Double.parseDouble(s));
                }
                if (value != null) {
                    op = Op.NONE;
                    value = Math.sqrt(value);
                    tvValue.setText("" + value);
                }
            }
        });

        final Button buttonLn = (Button) this.findViewById(R.id.buttonLn);
        if (buttonLn != null) {
            buttonLn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String s = tvValue.getText().toString();
                    if (validArg(s)) {
                        apply(Op.NONE, Double.parseDouble(s));
                    }
                    if (value != null) {
                        op = Op.NONE;
                        value = Math.log(value);
                        tvValue.setText("" + value);
                    }
                }
            });
        }

        final Button buttonSin = (Button) this.findViewById(R.id.buttonSin);
        if (buttonSin != null) {
            buttonSin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String s = tvValue.getText().toString();
                    if (validArg(s)) {
                        apply(Op.NONE, Double.parseDouble(s));
                    }
                    if (value != null) {
                        op = Op.NONE;
                        value = Math.sin(value);
                        tvValue.setText("" + value);
                    }
                }
            });
        }

        final Button buttonE = (Button) this.findViewById(R.id.buttonE);
        if (buttonE != null) {
            buttonE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvValue.setText("" + Math.E);
                }
            });
        }

        final Button buttonPi = (Button) this.findViewById(R.id.buttonPi);
        if (buttonPi != null) {
            buttonPi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvValue.setText("" + Math.PI);
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        final TextView tvValue = (TextView) this.findViewById(R.id.tvValue);
        b.putString("value", this.value==null ? null : "" + this.value);
        b.putString("etValue", tvValue.getText().toString());
        b.putInt("op", this.op.getValue());
    }
}