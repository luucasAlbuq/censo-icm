package util;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import icm.censo.a3_code.com.censoicm.R;
import model.Censo;

/**
 * Created by luucasAlbuq on 14/06/2017.
 */

public class RecycleViewAdapter extends  RecyclerView.Adapter<RecycleViewAdapter.CensoViewHolder> {

    private List<Censo> censoList;
    private Context context;
    private Calendar calendar = Calendar.getInstance();

    public RecycleViewAdapter(Context context, List<Censo> lista){
        this.censoList = lista;
        this.context = context;
    }

    @Override
    public CensoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        return new CensoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CensoViewHolder holder, int position) {
        if(holder.censoDate != null && holder.censoIgreja != null){
            Censo censo = censoList.get(position);
            Locale.setDefault(new Locale("pt", "BR"));
            SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
            String data = format.format(censo.getData());
            holder.censoDate.setText(data);
            holder.censoIgreja.setText(censo.getIgreja());
        }
    }

    @Override
    public int getItemCount() {
        return censoList.size();
    }

    public static class CensoViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView censoIcone;
        private TextView censoDate;
        private TextView censoIgreja;


        public CensoViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            censoDate = (TextView) itemView.findViewById(R.id.censo_date);
            censoIgreja = (TextView) itemView.findViewById(R.id.censo_igreja);
            censoIcone = (ImageView) itemView.findViewById(R.id.censo_icone);
        }
    }
}
