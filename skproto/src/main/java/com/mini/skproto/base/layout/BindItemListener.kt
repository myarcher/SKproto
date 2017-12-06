package com.mini.skproto.base.layout

import com.mini.skproto.base.CustomAdapter
import com.mini.skproto.base.ViHolder

/**
 *
 *
 * @author
 * @date    2017/10/13
 * @version 1.0
 */ 
public interface BindItemListener {
    fun bindItemHolder(holder: ViHolder, data: Map<String, Any>, position: Int, mAdapter: CustomAdapter)
} 