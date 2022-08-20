package com.pakachu.benfit.rv;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ContentInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pakachu.benfit.R;

import java.util.ArrayList;

public class HareketAdapter extends RecyclerView.Adapter<HareketAdapter.ViewHolder> {


    private Activity activity;
    ArrayList<HareketItem> hareketArrayList;
    Button btn_gun;

    private void GeriGetir(ViewHolder holder) {
        holder.btn_hareket.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.lefttorightreverse);
        holder.btn_hareket.startAnimation(animation);
    }

    private void SagaKaydir(ViewHolder holder) {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.righttoleft);
        holder.btn_hareket.startAnimation(animation);
        new CountDownTimer(700, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                holder.btn_hareket.setVisibility(View.GONE);
                if (holder.rv_detaylar.getVisibility() == View.VISIBLE)
                    holder.rv_detaylar.setVisibility(View.GONE);
            }
        }.start();
    }

    public HareketAdapter(Activity activity, ArrayList<HareketItem> hareketArrayList, Button btn_gun) {

        this.activity = activity;
        this.hareketArrayList = hareketArrayList;
        this.btn_gun = btn_gun;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hareket, parent, false);

        return new ViewHolder(view);
    }

    int i_toplam = 0;
    int i_yapilan = 0;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        final float len = activity.getResources().getDisplayMetrics().densityDpi / 6;
        View.OnTouchListener controlButtonTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initX = event.getX();
                        initY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        initX -= event.getX(); //now has difference value
                        initY -= event.getY(); //now has difference value
                        if (initX > len) {
//                            ((Button) v).setText("Swipe up");
                        } else if (initX < -len) { //SWIPE TO RIGHT
                            holder.cb_hareket.setChecked(true);
                        } else {
                            if (initY < 0) initY = -initY;
                            if (initX < 0) initX = -initX;
                            if (initX <= len / 4 && initY <= len / 4) { //CLICKED
                                if (holder.rv_detaylar.getVisibility() == View.VISIBLE)
                                    holder.rv_detaylar.setVisibility(View.GONE);
                                else
                                    holder.rv_detaylar.setVisibility(View.VISIBLE);
                            }
                        }
//                        ((Button) v).setText("len: " + len + "\nx: " + initX + "\ny: " + initY);
                        break;
                }
                return true;
            }

            float initX;
            float initY;


        };
        holder.itemView.findViewById(R.id.btn_hareket).setOnTouchListener(controlButtonTouchListener);


        holder.cb_hareket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SagaKaydir(holder);

                    String s = btn_gun.getText().toString();
                    i_toplam = Integer.parseInt(s.substring(s.indexOf('/') + 1, s.indexOf(')')));
                    i_yapilan = Integer.parseInt(s.substring(s.indexOf('(') + 1, s.indexOf('/')));
                    i_yapilan++;
                    btn_gun.setText(s.substring(0, s.indexOf("(")).trim() + " (" + i_yapilan + "/" + i_toplam + ")");
                    if(i_toplam==i_yapilan) {
                        Toast.makeText(activity, "Tebrikler! Günü tamamladınız.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    GeriGetir(holder);

                    String s = btn_gun.getText().toString();
                    i_toplam = Integer.parseInt(s.substring(s.indexOf('/') + 1, s.indexOf(')')));
                    i_yapilan = Integer.parseInt(s.substring(s.indexOf('(') + 1, s.indexOf('/')));
                    if(i_yapilan!=0)
                    i_yapilan--;
                    btn_gun.setText(s.substring(0, s.indexOf("(")).trim() + " (" + i_yapilan + "/" + i_toplam + ")");
                }
            }
        });

        HareketItem hareketItem = hareketArrayList.get(position);

        if (!hareketItem.hareketDetayi.equals(""))
            holder.btn_hareket.setText(hareketItem.hareketAdi + "\n" + hareketItem.hareketDetayi);
        else
            holder.btn_hareket.setText(hareketItem.hareketAdi);


        if (hareketItem.imageID != 0) {
            Drawable img = ResourcesCompat.getDrawable(activity.getResources(), hareketItem.imageID, null);
            img.setBounds(0, 0, 150, 150);
            holder.btn_hareket.setCompoundDrawables(img, null, null, null);
        }

        ArrayList<DetayItem> detayItemArrayList = new ArrayList<>();

        DetayItem detayItem = null;
        switch (hareketItem.hareketAdi) {
            case "ısınma":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Isınma hareketleri sayesinde bedeninizdeki kan dolaşımı artar böylece kas, ligament ve tendon gibi tüm dokular artan dolaşım ile egzersize hazırlanır. Isınma sadece bedeni değil aynı zamanda zihni de egzersize hazırlar. Beden, zihin ve sinir sisteminin hazırlanması ile eklemler spora uygun hale gelir.",R.drawable.isinmagif,R.drawable.isinmamusclesworked,"https://youtu.be/vtDrQJ6zCiE");
                break;
            case "kardiyo":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Hücreler, ihtiyaçları olan oksijeni ve besin maddelerini, dolaşım sistemi içinde bulunan kandan sağlamaktadır. Aynı zamanda da metabolizmaları sonucu ortaya çıkan karbondioksidi ve atık maddelerini atılım organlarına yönlendirmesi için kana vermektedirler. Kanın, tüm bu görevleri yerine getirmesini sağlayan sisteme ise dolaşım sistemi (kardiyovasküler sistem) denir. Kardiyo egzersizleri, kardiyovasküler sistemin güçlendirilmesine yardımcı olabilecek çeşitli egzersizleri ifade etmektedir. Bisiklet, ip atlama, yüzme ve koşu kardiyo egzersizleri arasında yer alır. Bu tür egzersizler, nefes alıp verme sıklığının artmasına yol açmaktadır. Bu da kişinin kalp atışında hızlanmaya ve buna bağlı olarak da hücrelerin daha fazla yağ yakmasını sağlamaktadır. Kardiyo, kalp ve akciğeri yoğun bir tempoda çalıştırarak, kişinin nefes alışverişini daha düzenli hale getirmektedir. Aynı zamanda da kalbin daha sağlıklı kan pompalamasına olumlu etkide bulunmaktadır.",R.drawable.kardiyogif,R.drawable.isinmamusclesworked,"https://youtu.be/-kFIwK71AFw");
                break;
            case "off":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Spor esnasında çalıştırılan kas, tendon ve eklemlerde gözle görülemeyecek kadar küçük travmalar meydana gelebilir. Özellikle kaslarda meydana gelen mikro yırtılmalar aslında daha güçlü ve büyük kas gruplarının oluşabilmesi için yaşanır. Dinlenme günü vererek vücudun bu mikro yaralanmaları iyileştirmesine zaman vermek daha güçlü bir bedene sahip olmanın ilk adımı olur. Mikro travmaların iyileşmesine izin verilmezse sürekli olarak fiziksel strese maruz bırakılan bedende spor esnasında yaralanma veya sürekli devam eden ağrı formunda kalıcı hasarlar yaşanabilir. Kemiklerin kendini yeniden şekillendirme süreci dahilinde spor sonucu yaşanan mikro çatlaklara iyileşme alanı tanınmazsa ciddi burkulmalara hatta kırılmalara yol açabilir. Dışarıdan ekstra bir fiziksel strese maruz kalmayan vücut kendi içinde dengelenme sürecine girer. Özellikle yeni bir egzersiz tipi veya spor günlük rutine dahil edilmeye başlanmışsa vücuda adapte olması için süre tanımak gerekir. Daha iyi bir kondisyona sahip olmak vücudun yapılan egzersizlere göre kas dokusunu, kemik yoğunluğunu ve kardiyovasküler hareketi uyarlamasından geçer. Yapılan egzersiz rutininin sürdürülebilir olması sporun bir zorunluluk veya bir ödev olarak görülmemesine dayanır. Dinlenme günleri vermek bedeni rahatlatırken “spor yapma gerekliliğinin” zihinde yaratabileceği baskının azalmasını, duygusal olarak yaşanabilecek tükenmişlik, bıkkınlık hislerinin engellenmesini ve sporu tamamen terk etme hissinin önüne geçilmesini sağlar. ",R.drawable.offdaygif);
                break;

                //GÖĞÜS
            case "bench press":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(), "Bench press Pectoralis majör – Göğüs kaslarını geliştirmeye yarayan temel egzersizdir. Üst vücut bölgesindeki birçok kasın gelişimin de fayda sağlamaktadır. Örneğin on omuz kasları, arka kol kasları, karın kasları, bacak kaslarının da aktivasyonu sağlamaktadır. Konsantre ve dikkatli bir şekilde hareketi yaptığımızda bu kaslarda çalışır.", R.drawable.benchpressgif, R.drawable.benchpressmusclesworked, "https://youtu.be/AIjsngj5xck");
                break;
            case "incline dumbell press":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(), "İncline dumbbell press hareketi, dik eğimli bench sehpası üzerinde, göğüs hizasında bulunan dumbbell’ları yere dik olarak iterek yapılan üst göğüs egzersizidir. Incline dumbbell bench press hareketi, çok etkili bir üst göğüs hareketidir. Bench press ile yapılış bakımından çok benzer tek farkı ise, sehpa açısının farklı olmasıdır. Bu farklılık ise, kaldırdığınız ağırlığı da düşürür.", R.drawable.incldumbellpressgif, R.drawable.incldumbellpressmusclesworked, "https://youtu.be/rKC9xLCRJ64");
                break;
            case "chest press":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(), "Chest press machine hareketi, göğüs kaslarını güçlendirmeye yardımcı olan bir egzersiz olmaktadır. Bunun yanında omuz bölgesindeki ve üst kol bölgesindeki kaslarında gelişiminde etkin rol almaktadır. Chest press machine hareketi sırasında vücudun en çok çalışan bölümü omuz ve kollar olmaktadır Bu yüzden bu bölgedeki kasların gelişmesine doğrudan etki etmektedir.", R.drawable.chestpressgif, R.drawable.macinepessmusclesworked, "https://youtu.be/xUm0BiZCWlQ");
                break;
            case "cable crossover":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(), "Cable Crossover, en çok göğüs kaslarını çalıştırır. Göğsün ortasından başlayarak Clavicle köprücük kemiğinin iç kısmı ve altıncı göğüs kafesi kemiğine kadar ulaşır. Aynı zamanda humerus üst kol kemiğine de bağlanır. Ön omuz kasını da çalıştırmaya yardımcı olur. Clavicle köprücük kemiğinin dış tarafından başlar ve Humerus üst kol kemiğine doğru bağlanır. Üst kol kasıdır. Kolun iç kısmında yer alan kısa kol kasıdır. Scapula omuz kemiğinden başlayarak ve Radius ön kol kemiğinin üst kısmına doğru bağlanır.", R.drawable.cablecrossovergif, R.drawable.cablecrossovermusclesworked, "https://youtu.be/taI4XduLpTk");
                break;
            case "dumbell fly":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Göğüsleri sıkıştırmaya ve ortadaki çizgiyi belirginleştirmeye yarayan hareket. dumbbells ile gerceklestirilen ve gogus kasini calistirmaya yarayan bir fitness hareketidir. ana göğüs egzersizi olmayıp yardımcı ve izole bir harekettir.",R.drawable.dumbellflygif,R.drawable.dumbellflymusclesworked,"https://youtu.be/MxfVDZrsdZ0");
                break;
            case "machine fly":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(), "Machine fly hareketi güzel bir sıkıştırma egzersizidir. Alt göğüsün gelişmesi için Lover major kas grubunun çalışması gerekir. Machine Fly ile alt göğüs kaslarını geliştirebilirsiniz. Derinlemesine bir göğüs arası istiyorsanız uygulamanız gereken egzersizler arasındadır. Üst Göğüs kaslarının gelişmesi için, göğüs hareketleri ile Up Pectoralis major kas gurubunun çalışması gerekir. Göğüs geliştirme hareketlerinden, Machine fly yaparak üst göğüs kaslarınızın gelişmesini sağlamış olursunuz. Ön Kol Kasları (Biceps): ön kol kası olan biceps brachii, pazularımızın gelişmesi için her ne kadar biceps hareketleri yapmamız gereksede Machine fly, doğru yapılması halinde genel olarak ön kol kaslarını çalıştıran hareketler arasında yer almaktadır. Serratus anterior, Boksör kası olarak bilinen serratus anterior kası, oldukça zor gelişen bir kas grubudur. Tam göğüs altı ile mide kasları arasında yer alan serratus antreior kası,  Machine fly hareketi ile çalışma sağlar.", R.drawable.machineflygif, R.drawable.machineflymusclesworked, "https://youtu.be/Z57CtFmRMxA");
                break;
            case "bicep curl":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(), "Bu hareket en temel hareketlerden bir tanesidir. Dumbell biceps curl hareketini doğru yapmak, birçok hareketi kolay bir şekilde yapmayı da beraberinde getirir. Bununla birlikte vücutta güzel bir görünüm sağlar. Kollar daha iri ve hacimli görünür. Bu kas grubu sırt çalışmak için oldukça önemli bir kas grubudur. ", R.drawable.bicepcurlgif, R.drawable.bicepcurlmusclesworked, "https://youtu.be/ykJmrZ5v0Oo");
                break;
            case "hammer curl":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(), "Dumbbell hammer curl, fitness yapan kişiler tarafından sıkça tercih edilen bir ön kol egzersizidir. Bu Egzersiz dumbbell kullanarak, avuç içleri birbirine bakar halde tutularak yapılan bir harekettir. Egzersiz esnasında dirsekler sabit tutularak ön kol kaslarının çalışması ve gelişmesi amaçlanır.", R.drawable.hammercurlgif, R.drawable.hammercurlmusclesworked, "https://youtu.be/WGTCVgCqLqM?list=TLPQMTkwNjIwMjI8fVjkes_Mvw");
                break;
//SIRT
            case "barfiks":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Barfiks, yüksek bir noktaya asılan bardan tutularak başın barın üzerine kaldırılma hareketidir. Barfiks, genel anlamda zor bir hareket olarak kabul edilir. Özellikle kol, sırt ve omuzların çalışmasını sağlar. Bu nedenle güç-kuvvet çalışmalarında getirisi çok yüksektir. Barfiks hareketi, insan vücudundaki kol kaslarının ne kadar güçlü olduğunu gösterir. Bu nedenle sporla ilgili meslek ve aktivitelerin hemen hepsinin programında barfiks yer almaktadır. Ayrıca polis, asker ya da güvenlik görevlisi gibi meslek gruplarının sınavlarında, adayların barfiks başarısı ayrı bir öneme sahiptir.",R.drawable.barfiksgif,R.drawable.barfiksmusclesworked,"https://youtu.be/Gq2oQx26emc");
                break;
            case "machine row":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Vücudun en büyük kas gruplarından biri olan sırt kasları, sağlık ve estetik açıdan oldukça önemlidir. Şekilli ve güçlü sırt kasları hem giydiğiniz kıyafetlerin üzerinizde daha iyi görünmesini sağlar hem de duruş ve oturuş bozukluklarının giderilmesine yardımcı olur.",R.drawable.machinerowgif,R.drawable.machinerowmusclesworked,"https://youtu.be/w49UGytisik");
                break;
            case "dumbell row":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Dumbbell row hareketi; esas olarak düz bir benchin üstüne bir dizin ve bir elin konularak üst vücudun yere paralel şekle getirip, tek kol aracılığı ile dumbbell'ı aşağıdan yukarı doğru çekmek sureti ile yapılmış olunan trapez, kanat ve sırt egzersizini ifade etmektedir.",R.drawable.dumbellrowgif,R.drawable.dumbellrowmusclesworked,"https://youtu.be/roCP6wCXPqo");
            break;
            case "lat pull":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Vücudun en büyük kas gruplarından biri olan sırt kasları, sağlık ve estetik açıdan oldukça önemlidir. Şekilli ve güçlü sırt kasları hem giydiğiniz kıyafetlerin üzerinizde daha iyi görünmesini sağlar hem de duruş ve oturuş bozukluklarının giderilmesine yardımcı olur.",R.drawable.latpullgif,R.drawable.latpullmusclesworked,"https://youtu.be/Z_3xHwuO8Tk");
                break;
            case "seated cable row":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Vücudun en büyük kas gruplarından biri olan sırt kasları, sağlık ve estetik açıdan oldukça önemlidir. Şekilli ve güçlü sırt kasları hem giydiğiniz kıyafetlerin üzerinizde daha iyi görünmesini sağlar hem de duruş ve oturuş bozukluklarının giderilmesine yardımcı olur.",R.drawable.seatedcablerowgif,R.drawable.seatedcablerowmusclesworked,"https://youtu.be/GZbfZ033f74");
                break;
            case "hyperextension":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Hyperextension ters mekik olarak da adlandırılabilen bir egzersizdir. Klasik mekik hareketini andırması sebebiyle bu adı almıştır. Mekik ile aynı mantığa sahip olan bu egzersiz, yüzüstü yatarak yapılır. Öncelikli amaç ise sırt kaslarını ekipmansız olarak geliştirebilmektir. Hızlı ya da yavaş tekrarlara müsait olan Hyperextension hareketi, bel omurlarının sağlığını korumak ve vücudun duruş pozisyonunu düzeltmek açısından da önemli faydalara sahiptir.",R.drawable.hyperextensiongif,R.drawable.hyperextensionmusclesworked,"https://youtu.be/ph3pddpKzzw");
                break;
            case "dumbell skullcrusher":
                detayItem=new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Ez-bar, dumbell ya da barbell yardımıyla, düz bench sehpası üzerinde, ağırlığı baş bölgesinin üstünden, göğüs bölgesine kaldırmak suretiyle yapılan, triceps brachii – arka kol kaslarını çalıştıran bir fitness hareketidir.",R.drawable.dumbellskullcrushergif,R.drawable.dumbellskullcrushermusclesworked,"https://youtu.be/ir5PsbniVSc");
                    break;
            case "close grip bench press":
                detayItem=new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Close grip bench press hareketi, bench press hareketine oldukça benzese de bir çeşit arka kol ARKA egzersizidir. Arka kol egzersizlerinizi yaparken bu hareketi tercih ederseniz hem Anterior deltoid (ön omuz) hem de Triceps Brachii (arka kol) kaslarınızı da çalıştırırsınız.",R.drawable.closegripbenchgif,R.drawable.closegripbenchmusclesworked,"https://youtu.be/wxVRe9pmJdk");
                break;
//BACAK
            case "squat":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(), "Ayakta durma pozisyonu ile çömelerek öne doğru eğilme pozisyonundan oluşan harekete squat denilmektedir. Squat demek kelime anlamı itibari ile \"çömelme\" demektir. Squat hareketi ile birlikte kalça ve diz kasları sürekli olarak çalışmaktadır. Squat hareketi ağırlık ile de yapılan bir harekettir. Hem ağırlık ile hem de ağırlık olmadan da yapılmaktadır.", R.drawable.squatgif, R.drawable.squatmusclesworked, "https://youtu.be/0B-v57Csk44");
                break;
            case "deadlift":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(), "Özellikle powerlifterların çok fazla haşır neşir olduğu bir hareket olan deadlift hareketi, büyük ve çoklu kas gruplarını bir arada çalıştırarak kas gelişimine bolca katkı sağlar. Aynı anda çoklu kas gruplarını bir arada hedef alan deadlift ve benzeri hareketler vücudun daha çok enerji üretmesine ve bu sayede kasları çalıştırarak daha çok kalori yakılmasına yardımcıdır. Bu nedenlerden ötürü deadlift ve bu minvaldeki hareketler vücut geliştirmeciler arasında oldukça popülerdir. Deadlift bir eğilme biçimidir ve eğilerek yerden belli bir miktarda ağırlık kaldırılarak uygulanır. Kalça kaslarını, sırt kaslarını ve trapezleri yoğun olarak çalıştırır. Yardımcı olarak çalıştırdığı kaslar ise kollar, bacaklar ve core bölgesidir. Böylece deadlift hangi kasları çalıştırır sorusuna da cevap verilmiş olunur.", R.drawable.deadliftgif, R.drawable.deadliftmusclesworked, "https://youtu.be/o5FvhPAZ_yw");
                break;
            case "lunge":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(), "Bacak ve kalça bölgesindeki büyük kas gruplarını inşa etmek amaçlı, Türkçe karşılığı ön hamle olarak bilinen egzersize lunge hareketi denmektedir. Özellikle vücudun alt bölgesini şekillendirmek ve güçlendirmek açısından faydalıdır. Üstelik bu egzersizleri yapmak için spor salonlarına gitmenize de gerek yok. Evde başlangıç seviyesinde lunge hareketine başlayabilir ya da ileri seviye varyasyonlarını farklı metotlarla uygulayarak bu egzersizlerinden farklı şekillerde faydalanabilirsiniz. Hem vücut sağlığınızı iyileştirmek hem de daha sıkı kalça ve bacaklara sahip olmanıza yardımcı olacak lunge egzersizi, kilo kaybına ek olarak simetrik bir vücuda katkıda bulunma gibi faydaları ile ön plana çıkmaktadır.", R.drawable.lungesgif, R.drawable.lungesmusclesworked, "https://youtu.be/qbPLDFf9LfI");
                break;
            case "leg press":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Leg press hareketi esasen leg press makinesi kullanılarak yapılmakta olan kalça ve üst bacak ve geliştirme egzersizi olarak kabul edilmektedir. Varyasyonlar ile dıi bacak, iç bacak ve kalflar da çalıştırılabilmektedir. Oldukça yüksek ağırlıklara girilmediği sürece ve aynı zamanda hareket formu bozulmadığı sürece bir hayli güvenli olmaktadır. Squat’tan sonra gelen en etkili nitelikteki bacak egzersizi olmaktadır. Leg press hareketinin yararlarına bakıldığında ise ön bacak kaslarını iyi düzeyde izole etmesinden dolayı kas gelişimi oldukça etkilidir. Set bitimlerinde çok daha hafif ağırlıklar ile yüksek tekrarlar yapmak da pump etkisini en sonuna kadar hissettirmektedir.",R.drawable.legpressgif,R.drawable.legpressmusclesworked,"https://youtu.be/QDnCR1eOOPw");
                break;
            case "leg extension":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Bacak geliştirme egzersizleri arasında bulunan Leg Extension, ağırlık makinesi aracılığıyla yapılır. Birbirinden farklı bölümlere sahip olan üst bacağın kısa sürede gelişmesi ve güçlenmesi bu hareket sayesinde mümkün olur. Egzersizin temel özelliği; makine sandalyesine oturup ağırlık barını iki ayak ile yukarı kaldırmaktan ibarettir.",R.drawable.legextensiongif,R.drawable.legextensionmusclesworked,"https://youtu.be/0EwFf_3niAg");
                break;
            case "leg curl":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Leg Curl Hareketi bacak grubunun arka bacak kısmındaki kasları çalıştıran bir harekettir. Bir makine yardımıyla yapılır. Yatarak yapılan arka bacak idmanı yüz üstü şekilde makineye yatılır ve ayaklar pedlere yaslanır eller ilede utma yerlerinden tutulur ayaklar kalçaya doğru çekilir. Bu hareket arka bacak kasına konsantre bir biçimde etki eder ve çalıştırır.",R.drawable.legcurlgif,R.drawable.legcurlmusclesworked,"https://youtu.be/SbSNUXPRkc8");
                break;
            case "calf raise":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Standing calf raise, kalf kaslarını geliştirmek için yapılan klasik ve standart bir hareketidir. Özellikle de Gastrocnemius kasının çalışmasına yardımcı olur. Bu hareketi yapmak için omuzlarınızı makineye yerleştirmeniz ve parmak uçlarınızın biraz daha ilerisinde durmanız gerekir. Topuğunuzu yere doğru kontrollü bir şekilde basarak indirmeli ve iyice esnemelisiniz. Bu şekilde sadece 2 saniye durmanız gerekir. Sonra tekrar yukarı doğru kaldırmanız gerekir",R.drawable.calfraisegif,R.drawable.calfraisemusclesworked,"https://youtu.be/-M4-G8p8fmc");
                break;
            case "dips":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Dips hareketi bir barfiks kadar etki sağlayan önemli bir çalışmadır. Özellikle ağır bir yük ile hareket yapıldığında bölge kasları çok daha etkin şekilde çalıştırılabilmektedir. Mutlaka dinlenmiş şekilde ve kas bölgesi çalışmaya hazır olduğunda, yorgun olmayan bir zamanda dips hareketlerinin yapılması gerekir.",R.drawable.dipsgif,R.drawable.dipsmusclesworked,"https://youtu.be/2z8JmcrW-As");
                break;
            case "dumbell shoulder press":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Dumbbell Shoulder Press egzersizi omuz kasları için çok önemli egzersizler arasında yer alır. Diğer ismi ile dumbbell omuz press egzersizi, vücuda paralel olarak, çene seviyesinde bulunan dumbbell'ları yukarı doğru iterek yapılan orta omuz yani lateral deltoid egzersizidir.",R.drawable.dumbellshoulderpressgif,R.drawable.dumbellshoulderpressmusclesworked,"https://youtu.be/k9Y8meEPHAA");
                break;
            case "lateral raise":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Lateral raise – dumbbell yana açış hareketi; her iki ele alınan dumbbell'lar yardımıyla, yere sarkık halde duran kolları yanlara açmak suretiyle yapılan lateral deltoid – orta omuz kasını çalıştıran bir omuz egzersizidir.",R.drawable.lateralraisegif,R.drawable.lateralraisemusclesworked,"https://youtu.be/a4lcU0WMiIY");
                break;
            case "front raise":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Dumbell front raise; omuz egzersizidir. Oturarak ya da ayakta yapılabilmektedir. Omuz eklemleriyle yapılan egzersiz, omzun ön bölümünü çalıştırır. Hareket; çift ya da tek kolla yapılmaktadır. Bu hareket, başlangıç aşamasıyla beraber yapılıyorsa set içerisinde kolları ara sıra çalıştırmak mantıklı olacaktır. Bunun nedeni iki kol beraber kaldırıldığı zaman bele çok yük binmesinden kaynaklıdır.",R.drawable.frontraisegif,R.drawable.frontraisemusclesworked,"https://youtu.be/-t7fuZ0KhDA?list=TLPQMjEwNjIwMjKxz0sD6XNo9Q");
                break;
            case "rear delt fly":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"rear delt fly veya dumbell reverse fly olarak da bilinen rear delt fly, üst sırt kaslarınızı ve omuz kaslarınızı, özellikle arka deltoidlerinizi veya arka deltoidlerinizi hedef alan bir ağırlık antrenmanı egzersizidir.",R.drawable.reardeltflygif,R.drawable.reardeltflymusclesworked,"https://youtu.be/1FNDEePWTLc?list=TLPQMjEwNjIwMjKxz0sD6XNo9Q");
                break;
            case "facepull":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Face Pull, öncelikle omuzlarımızı özellikle de arka omuz başlarını ve arka deltoidleri çalıştırıp güçlendirip ve dayanıklı bir hale getiren egzersizdir. Ayrıca ön kol kaslarımızı da geliştirmektedir. Bir üst vücut egzersizidir. Omuz kaslarını daha güçlü ve daha hacimli görünümünü sağlamak için yapılacak bir egzersizdir.",R.drawable.facepullgif,R.drawable.facepullmusclesworked,"https://youtu.be/rep-qVOkqgk");
            break;
            case "preacher curl":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Kendine özel sehpasında çeşitli araçlar kullanılarak yapılan bu hareket açık ve kapalı tutuşlarla, çeşitli kasların ayrı ayrı çalışmasını sağlayan bir pazu geliştirme egzersizi olarak ifade edilmektedir.",R.drawable.preachercurlgif,R.drawable.preachercurlmusclesworked,"https://youtu.be/fIWP-FRFNU0?list=TLPQMjEwNjIwMjKxz0sD6XNo9Q");
                break;
            case "cable curl":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Cable Curl biceps hareketine odaklı bir harekettir. İstasyon makinelerinde bu hareket yapılabilir. Makara yardımıyla aşağıdan yukarı doğru çekilerek yapılan bir ön kol egzersizdir. Serbest ağırlığa göre daha basit olarak gelebilir bunun sebebi ise kamara sistemidir.",R.drawable.cablecurlgif,R.drawable.cablecurlmusclesworked,"https://youtu.be/85kXYq7Ssh4?list=TLPQMjEwNjIwMjKxz0sD6XNo9Q");
                break;
            case "rope pushdown":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Triceps pushdown hareketi; İstasyonda bar veya halat kullanılarak, ağırlığı yukarıdan aşağı çekmek suretiyle yapılan bir arka kol egzersizidir. Gelişim açısından harikalar yaratır. Tüm arka kol hareketlerinin arasından sıyrılıp ilk üçe girebileceğini rahatlıkla söyleyebiliriz.",R.drawable.ropepushdowngif,R.drawable.ropepushdownmusclesworked,"https://youtu.be/vB5OHsJ3EME?list=TLPQMjEwNjIwMjKxz0sD6XNo9Q");
                break;
            case "cable pushdown":
                detayItem = new DetayItem(hareketItem.hareketAdi.toUpperCase(),"Triceps pushdown hareketi; İstasyonda bar kullanılarak, ağırlığı yukarıdan aşağı çekmek suretiyle yapılan bir arka kol egzersizidir. Gelişim açısından harikalar yaratır.",R.drawable.cablepushdowngif,R.drawable.ropepushdownmusclesworked,"https://youtu.be/2-LAMcpzODU?list=TLPQMjEwNjIwMjKxz0sD6XNo9Q");
                break;
        }
        if (detayItem != null)
            detayItemArrayList.add(detayItem);


        if (detayItemArrayList.size() > 0) {
            DetayAdapter adapterMember = new DetayAdapter(activity, detayItemArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            holder.rv_detaylar.setLayoutManager(linearLayoutManager);
            holder.rv_detaylar.setAdapter(adapterMember);
        }


    }

    @Override
    public int getItemCount() {

        return hareketArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        CardView cv_hareket;
        Button btn_hareket;
        RecyclerView rv_detaylar;
        CheckBox cb_hareket;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_detaylar = itemView.findViewById(R.id.rv_detaylar);
            btn_hareket = itemView.findViewById(R.id.btn_hareket);
            cb_hareket = itemView.findViewById(R.id.cb_hareket);
            cv_hareket = itemView.findViewById(R.id.cv_hareket);
        }
    }


}