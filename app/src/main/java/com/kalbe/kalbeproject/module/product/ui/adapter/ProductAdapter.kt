package com.kalbe.kalbeproject.module.product.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kalbe.datasource.model.Product
import com.kalbe.kalbeproject.R
import com.kalbe.kalbeproject.databinding.ItemProductBinding

class ProductAdapter(private val products: ArrayList<Product>, private val callback: ProductItemCallback): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemProductBinding>(
            LayoutInflater.from(parent.context), R.layout.item_product,
            parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = products[position]
        holder.binding.data = data
        holder.binding.callback = callback

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ViewHolder(val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root)

    interface ProductItemCallback {
        fun onEditClicked(sku: String)
        fun onDeleteClicked(sku: String)
    }
}