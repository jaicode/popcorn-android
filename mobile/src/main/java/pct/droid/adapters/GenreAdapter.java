/*
 * This file is part of Popcorn Time.
 *
 * Popcorn Time is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Popcorn Time is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Popcorn Time. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * This file is part of Popcorn Time.
 *
 * Popcorn Time is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Popcorn Time is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Popcorn Time. If not, see <http://www.gnu.org/licenses/>.
 */

package pct.droid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pct.droid.R;
import pct.droid.base.providers.media.models.Genre;

public class GenreAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private View mSelectedItem;
    private int mSelectedPos = 0;
    private List<Genre> mData;
    private OnItemSelectionListener mItemSelectionListener;

    private int mSelectedColor, mNormalColor;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
            v.setOnClickListener(this);
        }

        @InjectView(android.R.id.text1)
        TextView text1;

        @Override
        public void onClick(View view) {
            if (mItemSelectionListener != null) {
                mSelectedPos = getPosition();
                mSelectedItem.setBackgroundColor(mNormalColor);
                mSelectedItem = itemView;
                mSelectedItem.setBackgroundColor(mSelectedColor);

                mItemSelectionListener.onItemSelect(view, getItem(mSelectedPos), mSelectedPos);
            }
        }

    }

    public GenreAdapter(Context context, List<Genre> data, int selectedPos) {
        mContext = context;
        mData = data;
        mSelectedPos = selectedPos;

        mSelectedColor = context.getResources().getColor(R.color.selectable_focused);
        mNormalColor = context.getResources().getColor(android.R.color.transparent);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_singleline_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        if(mSelectedPos == position && mSelectedItem == null)
            mSelectedItem = viewHolder.itemView;

        viewHolder.itemView.setBackgroundColor(mSelectedPos == position ? mSelectedColor : mNormalColor);
        viewHolder.text1.setText(getItem(position).getLabelId());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Genre getItem(int position) {
        return mData.get(position);
    }

    public void setOnItemSelectionListener(OnItemSelectionListener listener) {
        mItemSelectionListener = listener;
    }

    public interface OnItemSelectionListener {
        public void onItemSelect(View v, Genre item, int position);
    }
}
