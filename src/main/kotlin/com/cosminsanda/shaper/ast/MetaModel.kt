package com.cosminsanda.shaper.ast

interface Node

data class Shaper(val dim: Int, val rows: List<Row>): Node

data class Row(val shapes: List<Shape>): Node

data class Shape(val type: String): Node