package com.cosminsanda.shaper.ast

import com.cosminsanda.shaper.parsing.ShaperParser.ShapeContext
import com.cosminsanda.shaper.parsing.ShaperParser.RowContext
import com.cosminsanda.shaper.parsing.ShaperParser.ShaperContext

fun ShaperContext.toAst(): Shaper = Shaper(this.row().map { it.toAst() })

fun RowContext.toAst(): Row = Row(this.shape().map { it.toAst() })

fun ShapeContext.toAst(): Shape = this.toAst()