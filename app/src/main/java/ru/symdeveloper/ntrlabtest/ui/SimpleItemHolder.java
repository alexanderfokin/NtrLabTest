package ru.symdeveloper.ntrlabtest.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import ru.symdeveloper.ntrlabtest.R;
import ru.symdeveloper.ntrlabtest.model.SimpleListItem;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Alexander Fokin on 01.12.2015.
 */
@LayoutId(R.layout.list_item_simple)
public class SimpleItemHolder extends ItemViewHolder<SimpleListItem> {

        private static final String LOG_TAG = "SimpleItemHolder";

        @ViewId(R.id.fieldName)
        TextView fieldName;

        @ViewId(R.id.fieldValue)
        TextView fieldValue;

        private int position;

        public SimpleItemHolder(View view) {
            super(view);
        }

        @Override
        public void onSetValues(SimpleListItem content, PositionInfo positionInfo) {
            position = positionInfo.getPosition();
            fieldName.setText(content.getField());
            fieldValue.setText(content.getValue());
        }

}
