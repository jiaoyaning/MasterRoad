package com.jyn.masterroad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyTouchRecyclerView rv_layout;

    static int position = 0;
    static boolean isScroll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_layout = findViewById(R.id.rv_layout);
        rv_layout.setLayoutManager(layoutManager);
        RvAdapter adapter = new RvAdapter();
        rv_layout.setAdapter(adapter);
    }

    public void scroll(View view) {
        rv_layout.smoothScrollToPosition(++position);

    }

    public void isScroll(View view) {
        isScroll = !isScroll;
        ((Button) view).setText(String.valueOf(isScroll));
        rv_layout.setCanScroll(isScroll);
    }

    public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_test, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.button.setText("按钮：" + position);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 50;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            Button button;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                button = itemView.findViewById(R.id.button);
            }
        }
    }
}
