# NetLogo-graph6 extension

This is an extension for export a network to a graph6 format file.

## Usage

#### Primitive `save-graph6`

`graph6:save-graph6 turtle-set link-set file-name`

Saves the network in graph6 format, according to the adjacency matrix construted by a graph with `turtle-set` nodes and `links-set` edges, into `file-name` file.

#### Primitive `save-cvs`

`graph6:save-csv turtle-set link-set file-name`

Saves the number of nodes and a list of edges in the form `node1 node2` to represent the network, in csv format.

## Building

Run `make`.

If compilation succeeds, `graph6.jar` will be created.

## License

GNU GPL v3
