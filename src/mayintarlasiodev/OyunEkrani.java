/*
 
 -2 : açılanlar
 -1 : mayın
 0  :açılmamış olanlar

 */
package mayintarlasiodev;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.Timer;


public class OyunEkrani extends javax.swing.JFrame {
    
    private static OyunEkrani oyunekrani;
    
    public static OyunEkrani ekraniAl(){
        if(oyunekrani==null){
            return new OyunEkrani();
        }
        return oyunekrani;
    }
    
    public int zaman = 0;
    public int satir = 13; 
    public int sutun = 13; 
    public int mayin = 25;
    int kalan_mayin;
    int say_bayrak = 0;
    int puan;
    public String sonpuan;
    int geri_sayim = 200;
    
    int [][] mayindeg = new int [sutun][satir] ;
    
    JToggleButton[][] bloklar = new JToggleButton[sutun][satir];
    
    boolean ilk , oyna ,acikmi;
    
    Random rand = new Random();
    
    ActionListener listen = new ActionListener() 
    {
        public void actionPerformed (ActionEvent e)
        {
            int i = 0 ,j = 0;
            
            boolean varmi = false;
            
            for( i=0 ; i < sutun ; i++)
            {
                for( j=0 ; j < satir ; j++)
                {
                    if(e.getSource()==bloklar[i][j])
                    {
                        varmi = true;
                        
                        break;
                    }    
                }
                if (varmi) break;
            }
            if(oyna)
            {
                t.start();
            bloklar[i][j].setSelected(true);
                if(!ilk)
                {
                    olustur(i ,j);
                    ilk = true; 
                }
                if(mayindeg[i][j] != -1)
                {
                    ac(i,j);
                    kural();
                    renklendir();
                    bayrak();
                    
                }
                else yenilgi();
                kazandimi();
            }
            else kural();
        }
    };  
    
    
    private void kazandimi()
    {
        boolean kazanmak = true;
        
        for(int i=0 ;i < sutun ; i++)
        {
            for( int j=0 ;j < satir ; j++)
            {
                if(mayindeg[i][j] == 0)
                {
                    kazanmak=false;
                    break;
                }
            }
            
            if (!kazanmak) break;
        } 
        if(kazanmak)
        {
            javax.swing.JOptionPane.showMessageDialog(null, "YOU WİN!");
            oyna=false;
        }
    }
    

    
    private void yenilgi()
    {
        oyna = false ;
        
        for(int i=0 ; i<sutun ; i++)
        {
           for(int j=0 ; j<satir ; j++)
           {
               if (mayindeg[i][j] == -1)
               {
                   bloklar[i][j].setIcon(new ImageIcon(getClass().getResource("mine.gif")));
                   bloklar[i][j].setSelected(true);
                   t.stop();
                   say_bayrak = 0;
               }
            }
        }
    }
    
       Timer t = new Timer (1000 , new ActionListener() 
       {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                zaman++;
                geri_sayim--;
                gecen_zaman.setText(zaman+"");
                geri_say.setText(geri_sayim+"");
                if(geri_sayim >= 0)
                {
                    mayin_sayisi.setText(kalan_mayin+"");
                    if(geri_sayim == 0)
                    {
                       oyna = false; 
                       javax.swing.JOptionPane.showMessageDialog(null, "SÜRE DOLDU!");
                    }
                }
                puan = say_bayrak*10;
                puanim.setText(puan +"");
               
            }
           
        });
       
    public void setPuanim(JTextField puanim) {
        this.puanim = puanim;
    }
    

    private void ac(int y,int x) //Hazır kod
    {
        if(y < 0 || x < 0|| x > satir-1 || y > sutun-1 || mayindeg[y][x] != 0 ) return; 
        
        int bomba=0;
        
        for(int i=y-1 ;i<= y+1 ; i++)
        {
            for(int j=x-1 ; j<= x+1 ; j++)
            {
                if(!(j<0 || i<0 || j>satir-1 ||i>sutun-1)&& mayindeg[i][j] == -1)
                {
                    bomba++;
                }
            }
        }
        
        if(bomba == 0)
        {
            mayindeg[y][x] = -2; //Burada açılan bloklara -2 değerini verdim.
            for(int i=y-1;i<=y+1;i++)
            {
                for(int j=x-1;j<=x+1;j++)
                {
                    if(!(j<0 || i<0 || j>satir-1 ||i>sutun-1))
                    if(i!=y || j!=x)
                    {
                        ac(i,j);
                        acikmi = true;
                    }
                    else acikmi = false;
                
                }
            }
            
        }
        else  mayindeg[y][x] = bomba;         
        
    }
    
   
    public void bayrak(){
       kalan_mayin = mayin;
       for(int i=1 ; i<sutun-1 ; i++)
        {
           for(int j=1 ; j<satir-1 ; j++)
           {
               if (mayindeg[i][j] == -1)
               {
                   if((mayindeg[i-1][j] != 0 || mayindeg[i-1][j] == -1) && (mayindeg[i+1][j] != 0 || mayindeg[i+1][j] == -1) && (mayindeg[i-1][j-1] != 0 || mayindeg[i-1][j-1] == -1) 
                          && (mayindeg[i][j-1] != 0 || mayindeg[i][j-1] == -1)  && (mayindeg[i+1][j-1] != 0 || mayindeg[i+1][j-1] == -1) && ( mayindeg[i-1][j+1] != 0 || mayindeg[i-1][j+1] == -1 ) && (mayindeg[i][j+1] != 0 || mayindeg[i][j+1] == -1) && (mayindeg[i+1][j+1] != 0 || mayindeg[i+1][j+1] == -1))
                   {
                    if(mayindeg[i-1][j] >= -2 && mayindeg[i+1][j] >= -2 && mayindeg[i-1][j-1] >= -2 && mayindeg[i][j-1] >= -2 && mayindeg[i+1][j-1] >= -2 && mayindeg[i-1][j+1] >= -2 && mayindeg[i][j+1] >= -2 && mayindeg[i+1][j+1] >= -2 )
                       {
                           if(acikmi == true)
                           {
                                bloklar[i][j].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++;
                              
                           }   
                       }   
                    } 
                }
            }
        }
        for(int i=1 ; i<sutun-1 ; i++)
        {
           for(int j=1 ; j<satir-1 ; j++)
           {
               if (mayindeg[i][j] == -1)
               {
                   if((mayindeg[i-1][j] != 0 || mayindeg[i-1][j] == -1) && (mayindeg[i+1][j] != 0 || mayindeg[i+1][j] == -1) && (mayindeg[i-1][j-1] != 0 || mayindeg[i-1][j-1] == -1) 
                          && (mayindeg[i][j-1] != 0 || mayindeg[i][j-1] == -1)  && (mayindeg[i+1][j-1] != 0 || mayindeg[i+1][j-1] == -1) && ( mayindeg[i-1][j+1] != 0 || mayindeg[i-1][j+1] == -1 ) && (mayindeg[i][j+1] != 0 || mayindeg[i][j+1] == -1) && (mayindeg[i+1][j+1] != 0 || mayindeg[i+1][j+1] == -1))
                   {
                           if(acikmi == true)
                           {
                               if(mayindeg[i-1][j] == -1)
                                {
                                bloklar[i-1][j].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++; 
                                }
                               else if(mayindeg[i+1][j] == -1)
                                {
                                bloklar[i+1][j].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++; 
                                }
                               else if(mayindeg[i-1][j-1] == -1)
                                {
                                bloklar[i-1][j-1].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++; 
                                }
                               else if(mayindeg[i][j-1] == -1)
                                {
                                bloklar[i][j-1].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++; 
                                }
                               else if(mayindeg[i+1][j-1] == -1)
                                {
                                bloklar[i+1][j-1].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++; 
                                }
                               else if(mayindeg[i-1][j+1] == -1)
                                {
                                bloklar[i-1][j+1].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++; 
                                }
                                else if(mayindeg[i][j+1] == -1)
                                {
                                bloklar[i][j+1].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++; 
                                }
                                else if(mayindeg[i+1][j+1] == -1)
                                {
                                bloklar[i+1][j+1].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++; 
                                }   
                           
                       }
                       
                    }
               }
           }
        }   
        for(int j=2 ; j<satir-2 ; j++)
           {
             if (mayindeg[j][0] == -1)
               {
                  if((mayindeg[j-1][0] != 0 || mayindeg[j-1][0] == -1) && (mayindeg[j+1][0] != 0 || mayindeg[j+1][0] == -1) && (mayindeg[j-1][1] != 0 || mayindeg[j-1][1] == -1) 
                          && (mayindeg[j][1] != 0 || mayindeg[j][1] == -1)  && (mayindeg[j+1][1] != 0 || mayindeg[j+1][1] == -1) )
                   {  
                      bloklar[j][0].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++;  
                   }
               }   
           }
         for(int j=2 ; j<satir-2 ; j++)
           {
             if (mayindeg[j][sutun-1] == -1)
               {
                  if((mayindeg[j-1][sutun-1] != 0 || mayindeg[j-1][sutun-1] == -1) && (mayindeg[j+1][sutun-1] != 0 || mayindeg[j+1][sutun-1] == -1) && (mayindeg[j-1][sutun-2] != 0 || mayindeg[j-1][sutun-2] == -1) 
                          && (mayindeg[j][sutun-2] != 0 || mayindeg[j][sutun-2] == -1)  && (mayindeg[j+1][sutun-2] != 0 || mayindeg[j+1][sutun-2] == -1) )
                   {  
                      bloklar[j][sutun-1].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++;  
                   }
               }   
           }
          for(int j=2 ; j<sutun-2 ; j++)
           {
             if (mayindeg[0][j] == -1)
               {
                  if((mayindeg[0][j-1] != 0 || mayindeg[0][j-1] == -1) && (mayindeg[0][j+1] != 0 || mayindeg[0][j+1] == -1) && (mayindeg[1][j-1] != 0 || mayindeg[1][j-1] == -1) 
                          && (mayindeg[1][j] != 0 || mayindeg[1][j] == -1)  && (mayindeg[1][j+1] != 0 || mayindeg[1][j+1] == -1) )
                   {  
                      bloklar[0][j].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++;  
                   }
               }   
           }
                  for(int j=2 ; j<sutun-2 ; j++)
           {
             if (mayindeg[satir-1][j] == -1)
               {
                  if((mayindeg[satir-1][j-1] != 0 || mayindeg[satir-1][j-1] == -1) && (mayindeg[satir-1][j+1] != 0 || mayindeg[satir-1][j+1] == -1) && (mayindeg[satir-2][j-1] != 0 || mayindeg[satir-2][j-1] == -1) 
                          && (mayindeg[satir-2][j] != 0 || mayindeg[satir-2][j] == -1)  && (mayindeg[satir-2][j+1] != 0 || mayindeg[satir-2][j+1] == -1) )
                   {  
                      bloklar[satir-1][j].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++;  
                   }
               }   
           }
        if((mayindeg[0][sutun-1] == -1))
        {
            if((mayindeg[1][sutun-1] != 0 || mayindeg[1][sutun-1] == -1) && (mayindeg[0][sutun-2] != 0 || mayindeg[0][sutun-2] == -1) && (mayindeg[1][sutun-2] != 0 || mayindeg[1][sutun-2] == -1))
                         
                   {  
                     bloklar[0][sutun-1].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++;    
                   } 
        }
        if((mayindeg[0][0] == -1))
        {
            if((mayindeg[0][1] != 0 || mayindeg[0][1] == -1) && (mayindeg[1][0] != 0 || mayindeg[1][0] == -1) && (mayindeg[1][1] != 0 || mayindeg[1][1] == -1))
                         
                   {  
                     bloklar[0][0].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++;    
                   } 
        }
        if((mayindeg[satir-1][0] == -1))
        {
            if((mayindeg[satir-1][1] != 0 || mayindeg[satir-1][1] == -1) && (mayindeg[satir-2][0] != 0 || mayindeg[satir-2][0] == -1) && (mayindeg[satir-2][1] != 0 || mayindeg[satir-2][1] == -1))
                         
                   {  
                     bloklar[satir-1][0].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++;    
                   } 
        }
        if((mayindeg[satir-1][sutun-1] == -1))
        {
            if((mayindeg[satir-1][sutun-2] != 0 || mayindeg[satir-1][sutun-2] == -1) && (mayindeg[satir-2][sutun-1] != 0 || mayindeg[satir-2][sutun-1] == -1) && (mayindeg[satir-2][sutun-2] != 0 || mayindeg[satir-2][sutun-2] == -1))
                         
                   {  
                     bloklar[satir-1][sutun-1].setIcon(new ImageIcon(getClass().getResource("flag.gif")));
                                kalan_mayin--;
                                say_bayrak++;    
                   } 
        }
        
    }

    public int getSay_bayrak() {
        return say_bayrak;
    }

    
public void panelAyar(int a, int b ,int c)
{      panela.removeAll();
       satir=a;
       sutun=b;
       mayin=c;
       panela.repaint();
       panela.revalidate();
       paneleEkle();
        
       
}

    private void renklendir()
    {
       for(int i=0 ; i<sutun ; i++)
        {
            for( int j=0 ; j<satir ; j++)
             {
              if (mayindeg[i][j] == 1)     
                {
                  bloklar[i][j].setIcon(new ImageIcon(getClass().getResource("1.gif")));   
                } 
               if (mayindeg[i][j] == 2)     
                {
                  bloklar[i][j].setIcon(new ImageIcon(getClass().getResource("2.gif")));   
                } 
               if (mayindeg[i][j] == 3)     
                {
                  bloklar[i][j].setIcon(new ImageIcon(getClass().getResource("3.gif")));   
                } 
               if (mayindeg[i][j] == 4)     
                {
                  bloklar[i][j].setIcon(new ImageIcon(getClass().getResource("4.gif")));   
                } 
               if (mayindeg[i][j] == 5)     
                {
                  bloklar[i][j].setIcon(new ImageIcon(getClass().getResource("5.gif")));   
                } 
               if (mayindeg[i][j] == 6)     
                {
                  bloklar[i][j].setIcon(new ImageIcon(getClass().getResource("6.gif")));   
                } 
               if (mayindeg[i][j] == 7)     
                {
                  bloklar[i][j].setIcon(new ImageIcon(getClass().getResource("7.gif")));   
                } 
               if (mayindeg[i][j] == 8)     
                {
                  bloklar[i][j].setIcon(new ImageIcon(getClass().getResource("8.gif")));   
                } 
             } 
        }
    }
    
    
    private void kural(){
        for(int i=0 ; i<sutun ; i++)
        {
            for( int j=0 ; j<satir ; j++)
             {
                if (mayindeg[i][j] == 0)
                     
                {
                    bloklar[i][j].setText("");
                    bloklar[i][j].setSelected(false);   
                }
                if(mayindeg[i][j] == -2)
                {
                    bloklar[i][j].setText("");
                    bloklar[i][j].setSelected(true);
                    
                     
                }
                if(mayindeg[i][j] > 0)
                {
                    bloklar[i][j].setText("");
                    bloklar[i][j].setSelected(true);
                     
                } 
                if(!oyna && mayindeg[i][j] == -1 )
                { 
                    bloklar[i][j].setSelected(true);
                }
            }
        }  
        panela.repaint();
    }
    
    
    
    private void olustur(int y , int x)
    {
        for(int k=1 ; k<=mayin ; k++)
        {
            int i,j;
            do
            {
                 i = (int)(Math.random()*(satir-.01));
                 j = (int)(Math.random()*(sutun-.01)); 
            }
            while(mayindeg[i][j] == -1 || (i == y && j == x));
            
            mayindeg[i][j]= -1 ;  
            
        }
        
    }
    
    public void paneleEkle()
    {
        for(int i=0 ; i<sutun ; i++)
        {
            for(int j=0 ; j<satir ; j++)
            {
                bloklar[i][j] = new JToggleButton();
                bloklar[i][j].setSize( panela.getWidth()/satir , panela.getHeight()/sutun );
                panela.add(bloklar[i][j]);
                bloklar[i][j].setLocation(j*panela.getWidth()/satir, i*panela.getHeight()/sutun);
                bloklar[i][j].addActionListener(listen); 
            }
        }
        oyna = true; 
    }

 
    public OyunEkrani() 
    {
        initComponents();
        setLocation(450,150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MAYIN TARLASI OYUNU");
        setResizable(false);
        for(int i=0 ; i<sutun ; i++)
        {
            for(int j=0 ; j<satir ; j++)
            {
                bloklar[i][j] = new JToggleButton();
                bloklar[i][j].setSize( panela.getWidth()/satir , panela.getHeight()/sutun );
                panela.add(bloklar[i][j]);
                bloklar[i][j].setLocation(j*panela.getWidth()/satir, i*panela.getHeight()/sutun);
                bloklar[i][j].addActionListener(listen); 
            }
        }
        ilk = false;
        oyna = true; 
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panela = new javax.swing.JPanel();
        panelb = new javax.swing.JPanel();
        mayin_sayisi = new javax.swing.JTextField();
        gecen_zaman = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        geri_say = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        puanim = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        panelc = new javax.swing.JPanel();
        yeni_oyun = new javax.swing.JButton();
        skor = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        kolay = new javax.swing.JMenuItem();
        orta = new javax.swing.JMenuItem();
        zor = new javax.swing.JMenuItem();
        ayarlar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        nasil = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panela.setBackground(new java.awt.Color(102, 102, 102));
        panela.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelaLayout = new javax.swing.GroupLayout(panela);
        panela.setLayout(panelaLayout);
        panelaLayout.setHorizontalGroup(
            panelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelaLayout.setVerticalGroup(
            panelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );

        mayin_sayisi.setBackground(new java.awt.Color(0, 0, 0));
        mayin_sayisi.setFont(new java.awt.Font("Book Antiqua", 1, 24)); // NOI18N
        mayin_sayisi.setForeground(new java.awt.Color(204, 0, 0));
        mayin_sayisi.setText("0");
        mayin_sayisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mayin_sayisiActionPerformed(evt);
            }
        });

        gecen_zaman.setBackground(new java.awt.Color(0, 0, 0));
        gecen_zaman.setFont(new java.awt.Font("Book Antiqua", 1, 24)); // NOI18N
        gecen_zaman.setForeground(new java.awt.Color(204, 0, 0));
        gecen_zaman.setText("0");
        gecen_zaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gecen_zamanActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mayintarlasiodev/mine.gif"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mayintarlasiodev/time.png"))); // NOI18N

        geri_say.setBackground(new java.awt.Color(0, 0, 0));
        geri_say.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        geri_say.setForeground(new java.awt.Color(204, 0, 0));
        geri_say.setText("200");
        geri_say.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geri_sayActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mayintarlasiodev/k.png"))); // NOI18N

        puanim.setBackground(new java.awt.Color(0, 0, 0));
        puanim.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        puanim.setForeground(new java.awt.Color(255, 255, 255));
        puanim.setText("0");
        puanim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puanimActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ink Free", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 0, 0));
        jLabel5.setText("PUAN");

        javax.swing.GroupLayout panelbLayout = new javax.swing.GroupLayout(panelb);
        panelb.setLayout(panelbLayout);
        panelbLayout.setHorizontalGroup(
            panelbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelbLayout.createSequentialGroup()
                .addGroup(panelbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gecen_zaman, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(panelbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(geri_say, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(62, 62, 62)
                .addGroup(panelbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(puanim, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(panelbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mayin_sayisi, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        panelbLayout.setVerticalGroup(
            panelbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelbLayout.createSequentialGroup()
                .addGroup(panelbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelbLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(panelbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)))
                    .addGroup(panelbLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelbLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(gecen_zaman, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(geri_say, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mayin_sayisi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(puanim, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        yeni_oyun.setBackground(new java.awt.Color(255, 102, 0));
        yeni_oyun.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        yeni_oyun.setForeground(new java.awt.Color(255, 255, 255));
        yeni_oyun.setText("Yeni Oyun");
        yeni_oyun.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        yeni_oyun.setSelected(true);
        yeni_oyun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yeni_oyunActionPerformed(evt);
            }
        });

        skor.setBackground(new java.awt.Color(255, 102, 0));
        skor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        skor.setForeground(new java.awt.Color(255, 255, 255));
        skor.setText("Skorlar");
        skor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skorActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mayintarlasiodev/kupa.png"))); // NOI18N

        javax.swing.GroupLayout panelcLayout = new javax.swing.GroupLayout(panelc);
        panelc.setLayout(panelcLayout);
        panelcLayout.setHorizontalGroup(
            panelcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(yeni_oyun, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(2, 2, 2)
                .addComponent(skor, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        panelcLayout.setVerticalGroup(
            panelcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcLayout.createSequentialGroup()
                .addGap(0, 21, Short.MAX_VALUE)
                .addGroup(panelcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(yeni_oyun, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(skor))))
        );

        jMenu1.setText("Seçenekler");

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mayintarlasiodev/level.jpg"))); // NOI18N
        jMenu3.setText("Oyun Seviyeleri");

        kolay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mayintarlasiodev/kolay.gif"))); // NOI18N
        kolay.setText("Kolay");
        kolay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kolayActionPerformed(evt);
            }
        });
        jMenu3.add(kolay);

        orta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mayintarlasiodev/orta.gif"))); // NOI18N
        orta.setText("Orta");
        orta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ortaActionPerformed(evt);
            }
        });
        jMenu3.add(orta);

        zor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mayintarlasiodev/zor.gif"))); // NOI18N
        zor.setText("Zor");
        zor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zorActionPerformed(evt);
            }
        });
        jMenu3.add(zor);

        jMenu1.add(jMenu3);

        ayarlar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mayintarlasiodev/ayar.gif"))); // NOI18N
        ayarlar.setText("Ayarlar");
        ayarlar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ayarlarActionPerformed(evt);
            }
        });
        jMenu1.add(ayarlar);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Yardım");

        nasil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mayintarlasiodev/yardim.gif"))); // NOI18N
        nasil.setText("Nasıl Oynanır?");
        nasil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nasilActionPerformed(evt);
            }
        });
        jMenu2.add(nasil);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(panelc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(panela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kolayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kolayActionPerformed

       panelAyar(8,8,8);
    }//GEN-LAST:event_kolayActionPerformed

    private void nasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nasilActionPerformed
       Nasil n = new Nasil();
       n.setLocation(300, 300);
       n.setResizable(false);
       n.setFocusable(false);
       n.setVisible(true);
    }//GEN-LAST:event_nasilActionPerformed

    private void gecen_zamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gecen_zamanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gecen_zamanActionPerformed

    private void yeni_oyunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeni_oyunActionPerformed
    Ayarlar ozelayar = new Ayarlar();
    oyna = false;
    gecen_zaman.setText("0");
    zaman = 0;
    geri_say.setText("200");
    geri_sayim = 200;
    puanim.setText("0");
    puan = 0;
    panela.removeAll();
    paneleEkle();
    panela.repaint();
    panela.revalidate();
    panela.updateUI();
    mayin_sayisi.setText("0");
    mayin_sayisi.updateUI();
        
            
    }//GEN-LAST:event_yeni_oyunActionPerformed

    private void mayin_sayisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mayin_sayisiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mayin_sayisiActionPerformed

    private void ortaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ortaActionPerformed
         panelAyar(12,12,15);
    }//GEN-LAST:event_ortaActionPerformed

    private void zorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zorActionPerformed
         panelAyar(13,13,25);
    }//GEN-LAST:event_zorActionPerformed

    public JPanel getPanela() {
        return panela;
    }

    public void setPanela(JPanel panela) {
        this.panela = panela;
    }

    private void geri_sayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geri_sayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_geri_sayActionPerformed

    private void skorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skorActionPerformed
       String s =puanim.getText();
       BaşarıListe skor = new BaşarıListe();
       skor.puan = s+"";
       skor.setLocation(300, 300);
       skor.setResizable(false);
       skor.setFocusable(false);
       skor.setVisible(true);
       
    }//GEN-LAST:event_skorActionPerformed

    private void puanimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puanimActionPerformed
    if (oyna == false)
    {
        puanim.setText(puanim.getText());
        
    }
    }//GEN-LAST:event_puanimActionPerformed

    private void ayarlarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ayarlarActionPerformed
        Ayarlar ozelayar = new Ayarlar();
        ozelayar.setLocation(300, 300);
        ozelayar.setResizable(false);
        ozelayar.setFocusable(false);
        ozelayar.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_ayarlarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OyunEkrani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OyunEkrani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OyunEkrani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OyunEkrani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OyunEkrani().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ayarlar;
    private javax.swing.JTextField gecen_zaman;
    private javax.swing.JTextField geri_say;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuItem kolay;
    private javax.swing.JTextField mayin_sayisi;
    private javax.swing.JMenuItem nasil;
    private javax.swing.JMenuItem orta;
    private javax.swing.JPanel panela;
    private javax.swing.JPanel panelb;
    private javax.swing.JPanel panelc;
    private javax.swing.JTextField puanim;
    private javax.swing.JButton skor;
    private javax.swing.JButton yeni_oyun;
    private javax.swing.JMenuItem zor;
    // End of variables declaration//GEN-END:variables
}
