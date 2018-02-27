package com.cosminsanda.shaper.ast

import com.cosminsanda.shaper.ShaperParser

fun ShaperParser.ShaperContext.toAst(): Shaper = Shaper(this.img_dim().text.toInt(), this.shp_dim().text.toInt(), this.row().map { it.toAst() })

fun ShaperParser.RowContext.toAst(): Row = Row(this.shape().map { it.toAst() })

fun ShaperParser.ShapeContext.toAst(): Shape = Shape(text)