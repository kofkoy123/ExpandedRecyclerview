package test.com.recyclerviewitemresize;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import test.com.recyclerviewitemresize.anim.ExpandableViewHoldersUtil;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    static final Random random = new Random(System.currentTimeMillis());

    public static class ColorName {
        final String name;
        final int color;

        public ColorName(String name) {
            this.name = name;
            this.color = Color.rgb(128 + random.nextInt(128), 128 + random.nextInt(128), 128 + random.nextInt(128));
        }
    }

    final ColorName[] contacts = new ColorName[]{
            new ColorName("Amanda M. Ormond"),
            new ColorName("Anquises Mejia Bustos"),
            new ColorName("Bellisima Goodchild"),
            new ColorName("Bodo Greenhand"),
            new ColorName("Brogan Allan"),
            new ColorName("Claudia Bosch"),
            new ColorName("Elisa Asmara"),
            new ColorName("Emile Barrientos"),
            new ColorName("Ermes Toscano"),
            new ColorName("Guarino Romani"),
            new ColorName("Heike Dresner"),
            new ColorName("Isobel Chamberlain"),
            new ColorName("Ja'rod Kahnrah"),
            new ColorName("Jessica L. Carrillo"),
            new ColorName("Joseph M. Parks"),
            new ColorName("Kabarann D'Ghor"),
            new ColorName("Karol Perea Paredes"),
            new ColorName("Kotkhe Varrin"),
            new ColorName("Kiera Pritchard"),
            new ColorName("Lavinia Sackville-Baggins"),
            new ColorName("Manville Dubois"),
            new ColorName("Marion Deslauriers"),
            new ColorName("Mewael Semere"),
            new ColorName("Negassi Girmay"),
            new ColorName("Numilen Sarabia Solano"),
            new ColorName("Ovidio Colombo"),
            new ColorName("Simone Hahn"),
            new ColorName("Stanley Ross"),
            new ColorName("Spencer Porter"),
            new ColorName("Tekle Ambessa"),
            new ColorName("Yasmin Alexander")
    };

    ExpandableViewHoldersUtil.KeepOneH<MainViewHolder> keepOne = new ExpandableViewHoldersUtil.KeepOneH<MainViewHolder>();

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        return new MainViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int pos) {
        final ColorName contact = contacts[pos];
        holder.bind(pos, contact.name, contact.color);
    }

    @Override
    public int getItemCount() {
        return contacts.length;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableViewHoldersUtil.Expandable {

        public final TextView contactNameTV;
        public final TextView infos;
        public final ImageView downAnim;
        private int height;
        private int width;

        public MainViewHolder(ViewGroup itemView) {
            super(itemView);
            contactNameTV = ((TextView) itemView.findViewById(R.id.contactName));
            infos = ((TextView) itemView.findViewById(R.id.infos));
            downAnim = ((ImageView) itemView.findViewById(R.id.down_anim));


            itemView.setOnClickListener(this);

            downAnim.postDelayed(new Runnable() {
                @Override
                public void run() {
                    height = downAnim.getHeight() / 2;
                    width = downAnim.getWidth() / 2;
                }
            }, 100);
        }

        public void bind(int pos, String name, int color) {
            contactNameTV.setText(name);
            keepOne.bind(this, pos);
            itemView.setBackgroundColor(color);
        }

        public void openAnim() {
            ObjectAnimator animator = ObjectAnimator.ofFloat(downAnim, "rotation", 0, 180).setDuration(400);
            downAnim.setPivotX(width);
            downAnim.setPivotY(height);
            animator.start();
        }


        public void closeAnim() {
            ObjectAnimator animator = ObjectAnimator.ofFloat(downAnim, "rotation", 180, 0).setDuration(400);
            downAnim.setPivotX(width);
            downAnim.setPivotY(height);
            animator.start();

        }

        public void openAnimQuike(){
            Log.e("lzr","启用=====开启动画");
            ObjectAnimator animator = ObjectAnimator.ofFloat(downAnim, "rotation", 0, 180).setDuration(0);
            downAnim.setPivotX(width);
            downAnim.setPivotY(height);
            animator.start();
            animator.cancel();
//            downAnim.setImageResource(R.drawable.icon_up);
        }

        public void closeAnimQuike() {
            Log.e("lzr","启用******关闭动画");
            ObjectAnimator animator = ObjectAnimator.ofFloat(downAnim, "rotation", 180, 0).setDuration(0);
            downAnim.setPivotX(width);
            downAnim.setPivotY(height);
            animator.start();
            animator.cancel();
//            downAnim.setImageResource(R.drawable.icon_down);
        }



        @Override
        public void onClick(View v) {
            keepOne.toggle(this);
        }

        @Override
        public View getExpandView() {
            return infos;
        }
    }
}
