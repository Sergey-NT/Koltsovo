package ru.koltsovo.www.koltsovo.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.koltsovo.www.koltsovo.ObjectPlane;
import ru.koltsovo.www.koltsovo.R;

public class ObjectPlaneAdapter extends BaseAdapter implements Filterable {

    private List<ObjectPlane> originalList;
    private List<ObjectPlane> filteredList;
    private LayoutInflater layoutInflater;
    private Context myContext;
    private ItemFilter itemsFilter = new ItemFilter();

    public ObjectPlaneAdapter(Context context, List<ObjectPlane> list) {
        myContext = context;
        this.originalList = list;
        this.filteredList = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        private TextView item0;
        private TextView item1;
        private TextView item2;
        private TextView item3;
        private TextView item4;
        private TextView item5;
        private TextView description1;
        private TextView description2;
        private RelativeLayout relativeLayout;
        private ImageView imageView;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setInfoTracking (int i) {
        boolean tracking;
        ObjectPlane objectPlane = getObjectPlane(i);
        tracking = objectPlane.isPlaneTracking();
        objectPlane.setPlaneTracking(!tracking);
        notifyDataSetChanged();
    }

    public boolean getInfoTracking (int i) {
        boolean tracking;
        ObjectPlane objectPlane = getObjectPlane(i);
        tracking = objectPlane.isPlaneTracking();
        return tracking;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.listview_item, viewGroup, false);

            holder = new ViewHolder();

            holder.item0 = (TextView) view.findViewById(R.id.tvPlaneFlight);
            holder.item1 = (TextView) view.findViewById(R.id.tvPlaneDirection);
            holder.item2 = (TextView) view.findViewById(R.id.tvPlaneType);
            holder.item3 = (TextView) view.findViewById(R.id.tvPlaneTimePlan);
            holder.item4 = (TextView) view.findViewById(R.id.tvPlaneTimeFact);
            holder.item5 = (TextView) view.findViewById(R.id.tvPlaneStatus);
            holder.description1 = (TextView) view.findViewById(R.id.tvPlaneTypeDesc);
            holder.description2 = (TextView) view.findViewById(R.id.tvPlaneStatusDesc);
            holder.relativeLayout = (RelativeLayout) view.findViewById(R.id.listViewItem);
            holder.imageView = (ImageView) view.findViewById(R.id.imageTracking);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ObjectPlane objectPlane = getObjectPlane(i);

        holder.item0.setText(objectPlane.getPlaneFlight());
        holder.item1.setText(objectPlane.getPlaneDirection());
        holder.item2.setText(objectPlane.getPlaneType());
        holder.item3.setText(objectPlane.getPlaneTimePlan());
        holder.item4.setText(objectPlane.getPlaneTimeFact());
        holder.item5.setText(objectPlane.getPlaneStatus());

        if (objectPlane.getPlaneType().equals("")) {
            holder.description1.setVisibility(View.GONE);
            holder.item2.setVisibility(View.GONE);
        } else {
            holder.description1.setVisibility(View.VISIBLE);
            holder.item2.setVisibility(View.VISIBLE);
        }

        if (objectPlane.isPlaneTracking()) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else  {
            holder.imageView.setVisibility(View.GONE);
        }

        switch (objectPlane.getPlaneStatus()) {
            case "Прибыл":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundGreen));
                break;
            case "Вылетел":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundGreen));
                break;
            case "Идет посадка":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundGreen));
                break;
            case "Идет регистрация":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundGreen));
                break;
            case "Отмена":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundRed));
                break;
            case "Регистрация закончена":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                break;
            case "Задержка":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                break;
            case "Задержка Позднее прибытие":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                break;
            case "Задержка Решение АК":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                break;
            case "Задержка Метеоусловия":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                break;
            case "Задержка Подготовка рейса":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                break;
            case "Задержка Регламент АП":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                break;
            case "Посадка закончена":
                holder.description2.setVisibility(View.VISIBLE);
                holder.item5.setVisibility(View.VISIBLE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorBackgroundYellow));
                break;
            default:
                holder.description2.setVisibility(View.GONE);
                holder.item5.setVisibility(View.GONE);
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(myContext, R.color.colorText));
                break;
            }
        return view;
    }

    private ObjectPlane getObjectPlane(int i) {
        return (ObjectPlane)getItem(i);
    }

    public Filter getFilter() {
        return itemsFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            int count = originalList.size();

            String filterableStringFlight;
            String filterableStringDirection;

            List<ObjectPlane> listWithFilter = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                filterableStringFlight = originalList.get(i).getPlaneFlight();
                filterableStringDirection = originalList.get(i).getPlaneDirection();

                if (filterableStringFlight.toLowerCase().contains(filterString) || filterableStringDirection.toLowerCase().contains(filterString)) {
                    listWithFilter.add(originalList.get(i));
                }
            }
            results.values = listWithFilter;

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<ObjectPlane>) results.values;
            notifyDataSetChanged();
        }
    }
}
