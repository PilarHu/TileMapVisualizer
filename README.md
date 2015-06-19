# TileMapVisualizer
Transforms text tilemap into image. Features:
* set tile size (width, height in pixel)
* outline tiles (variable outline width)
* offset even or odd rows with half tile width
* user defined colormap

## Options
* ``-h`` display help
* ``-x <num>`` set tile width (px)
* ``-y <num>`` set tile height (px)
* ``-cm <file>`` set colormap file
* ``-sho`` shift (offset) odd rows
* ``-she`` shift (offset) even rows
* ``-l <num>`` set outline width (px)

## Examples

#### Chess board
input text file:
```
W0W0W0W0
0W0W0W0W
W0W0W0W0
0W0W0W0W
W0W0W0W0
0W0W0W0W
W0W0W0W0
0W0W0W0W
```
colormap `basergb.properties`:
```
color.0=#000000
color.R=#FF0000
color.G=#00FF00
color.B=#0000FF
color.Y=#FFFF00
color.C=#00FFFF
color.M=#FF00FF
color.W=#FFFFFF
```

command line arguments:
`-cm basergb.properties`

image output:
![chess board tiles](https://raw.githubusercontent.com/PliarHu/TileMapVisualizer/master/examples/chess.png "Chess board tiles")
