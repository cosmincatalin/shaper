package com.cosminsanda.shaper.ast

interface Node

data class Shaper(val rows: List<Row>): Node

data class Row(val shapes: List<Shape>): Node

interface Shape: Node