package com.hr.toy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.toy.common.MyDividerItemDecoration;
import com.hr.toy.router.Page;
import com.hr.toy.router.Subject;
import com.hr.toy.utils.Jumper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectFragment extends BaseFragment {

    private Subject mSubject;
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        mSubject = (Subject) args.get(Subject.SUBJECT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_list, container, false);
        setUpView(view);
        return view;
    }

    private void setUpView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        initRecyclerView(mRecyclerView);
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        MyDividerItemDecoration itemDecoration = new MyDividerItemDecoration(getActivity());
//        itemDecoration.setDividerPadding(new MyDividerItemDecoration.DividerPadding() {
//            @Override
//            public int[] getDividerPadding(int position) {
//                return new int[]{48, 48};
//            }
//        });
//        recyclerView.addItemDecoration(itemDecoration);

        mAdapter = new ListAdapter(mSubject.getPages());
        mAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    Jumper.startActivity(getActivity(), mSubject.getPages().get(position).getPageClass());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        private List<Page> mData;
        private OnItemClickListener mOnItemClickListener;

        public ListAdapter(List<Page> data) {
            this.mData = data;
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mTextView.setText(mData.get(position).getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(android.R.id.text1)
            TextView mTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setBackgroundResource(R.drawable.touch_bg);
            }
        }private

        interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

    }
}
