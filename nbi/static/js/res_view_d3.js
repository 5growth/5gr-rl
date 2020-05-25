function representation(rawData) {
    //find the node index, in case some missing index in the json
    function find(f) {
            var i = -1
            rawData.nodes.forEach(function(node, index) {
                if (node.id == f)
                    i = index;
            });
            return i;
        }
        //set the source and the target index
    rawData.links.forEach(function(d) {
        d.source = find(d.source);
        d.target = find(d.target);
    })
    var groups = d3.nest().key(function(d) {
        return d.group;
    }).entries(rawData.nodes);
    var groupPath = function(d) {
        return "M" +
            d3.geom.hull(d.values.map(function(i) {
                return [i.x, i.y];
            }))
            .join("L") + "Z";
    };

    var groupFill = function(d, i) {
        return fill(i & 3);
    };

    var chartDiv = document.getElementById("chartd3");
    var h = chartDiv.clientHeight - 16,
        /*h = 600,*/
        w = chartDiv.clientWidth,
        fill = d3.scale.category10();
    //console.log(w, h, chartDiv.clientWidth, chartDiv.clientHeight, chartDiv);

    var vis = d3.select("#chartd3")
        .append("svg")
        .attr("width", "100%")
        .attr("height", h)
        //    .call(d3.behavior.zoom()
        //        .scaleExtent([1, 3])
        //        .center([w / 2, h / 2])
        //        .on("zoom", redraw))
    ;

    //function redraw() {
    //  trans = d3.event.translate;
    //  scale = d3.event.scale;
    //  vis.selectAll("path.link").attr("transform", "translate(" + trans + ")" + " scale(" + scale + ")");
    //  vis.selectAll("g.node").attr("transform", "translate(" + trans + ")" + " scale(" + scale + ")");
    ////  vis.selectAll("text").attr("transform", "translate(" + trans + ")" + " scale(" + scale + ")");
    //}

    var force = d3.layout.force()
        .charge(-300)
        .linkDistance(function(link) {
            return link.length;
        })
        //.linkStrength(0.1)
        .nodes(rawData.nodes)
        .links(rawData.links)
        .size([w, h])
        .start();

    // Per-type markers, as they don't inherit styles.
    vis.append("defs").selectAll("marker")
        .data(["call"])
        .enter().append("marker")
        .attr("id", function(d) {
            return d;
        })
        .attr("viewBox", "0 -5 10 10")
        .attr("refX", 16)
        .attr("refY", 0)
        .attr("markerWidth", 4)
        .attr("markerHeight", 4)
        .attr("orient", "auto")
        .append("path")
        .attr("d", "M0,-5L10,0L0,5");


    var path = vis.append("svg:g").selectAll("path.link")
        .data(force.links())
        .enter().append("path")
        .attr("class", function(d) {
            return "link " + d.type;
        })
        .attr("marker-end", function(d) {
            //console.log(d.type) ;
            return "url(#" + d.type + ")";
        });

    // Define the div for the tooltip
    var tooltip = d3.select("body").append("div")
        .attr("class", "tooltip")
        .style("opacity", 0);

    function dragend(d) {
      d3.select(this).classed("fixed", d.fixed = false);
    }

    function dragstart(d) {
      d3.select(this).classed("fixed", d.fixed = true);
    }
    var inner_drag = force.drag()
        .on("dragstart", dragstart);

    var node = vis.selectAll("node")
        .data(rawData.nodes)
        .enter().append("g")
        .attr("class", "node")
//        .on("dblclick", function(d) {
//            if (currentChild) currentChild.close();
//            if (d.type == "WIM") {
//                var wim_name = d.name;
//                currentChild = window.open("/wim_view/" + wim_name, "Test", "width=950,height=730");
//            }
//        })
        .on("dblclick", dragend)
        .on("mouseover", function(d) {
            tooltip.transition()
                .duration(200)
                .style("opacity", .9);
            tooltip	.html(d.type + ": " + d.name)
                .style("left", (d3.event.pageX + 20) + "px")
                .style("top", (d3.event.pageY - 28) + "px");
        })
        .on("mouseout", function(d) {
            tooltip.transition()
                .duration(500)
                .style("opacity", 0);
        })
        .call(inner_drag);


    node.append("image")
        .attr("xlink:href", function(d) {
            return d.img;
        })
        .attr("x", -17.5)
        .attr("y", function(d) {
            if (d.type == "OF-W") {
                return -35;
            } else {
                return -17.5;
            }
        })
        .attr("width", 35)
        .attr("height",  function(d) {
            if (d.type == "OF-W") {
                return 70;
            } else {
                return 35;
            }
        });

//    var text = vis.append("svg:g").selectAll(".text")
//        .data(rawData.nodes)
//        .enter().append("text")
//        .style("font-size", "9.5px")
//        .attr("x", function(d) {
//            return d.x;
//        })
//        .attr("y", function(d) {
//            if (d.type == "NFVIPOP") {
//                return d.y - 20;
//            } else {
//                return d.y - 14;
//            }
//        })
//        .text(function(d) {
//            return d.name;
//        })
//        //.call(force.drag)
//        ;

    vis.style("opacity", 1e-6)
        .transition()
        .duration(1000)
        .style("opacity", 1)
        //.style("align", "center")
        .style("border", 2);


    var hull = vis.selectAll("path.hull")
        .data(groups)
        .enter().insert("path", "g.node")
        .attr("class", "hull")
        .style("fill", groupFill)
        .style("stroke", groupFill)
        .style("stroke-width", "40px")
        .style("stroke-linejoin", "round")
        .style("opacity", .2)
        .on("mouseover", function(d) {
            tooltip.transition()
                .duration(700)
                .style("opacity", .9);
            tooltip.html(" WIM: " + d.key)
                .style("left", (d3.event.pageX + 20) + "px")
                .style("top", (d3.event.pageY - 28) + "px");
        })
        .on("mouseout", function(d) {
            tooltip.transition()
                .duration(500)
                .style("opacity", 0);
        })
        //.call(inner_drag)
        ;

    //var text_hull = vis.append("svg:g").selectAll(".text")
    //    .data(groups)
    //    .enter().append("text")
    //    .attr("class", "hull")
    //    .style("font", "15px helvetica")
    //    .attr("x", function(d) {
    //        return d.values[0].x;
    //    })
    //    .attr("y", function(d) {
    //        return d.values[0].y + 10;
    //    })
    //    .text(function(d) {
    //        return "WIM: " + d.key;
    //    })
    //    .call(force.drag);

    force.on("tick", function(e) {


        node.attr("transform", function(d) {
            return "translate(" + d.x + "," + d.y + ")";
        });
        hull.attr("d", groupPath);

        path.attr("d", function(d) {
            var dx = d.target.x - d.source.x,
                dy = d.target.y - d.source.y;
            if (d.type == "call") {
                dr = Math.sqrt(dx * dx + dy * dy) + 250;
            } else {
                dr = Math.sqrt(dx * dx + dy * dy);
            }
            return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;

        });

//        text.attr("x", function(d) {
//                return d.x;
//            })
//            .attr("y", function(d) {
//                if (d.type == "NFVIPOP") {
//                    return d.y - 20;
//                } else {
//                    return d.y - 14;
//                }
//            });

    });
    // Retrieve the array for
    var data_legend = [];
    for (var i=0; i < rawData.nodes.length; i++){
        data_legend.push({"name": rawData.nodes[i]["type"], "link": rawData.nodes[i]["img"]})
    }
    function removeDuplicates( arr, prop ) {
      var obj = {};
      for ( var i = 0, len = arr.length; i < len; i++ ){
        if(!obj[arr[i][prop]]) obj[arr[i][prop]] = arr[i];
      }
      var reduced_arr = [];
      for ( var key in obj ) reduced_arr.push(obj[key]);
      return reduced_arr;
    }
    data_legend_reduced = removeDuplicates(data_legend, "name");
    //Legend
    var legend = vis.append("svg:g").selectAll(".legend")
        .data(data_legend_reduced)
        .enter().append("g")
        .attr("class", "legend")
        .attr("transform", function(d, i) {
            return "translate(0," + i * 35 + ")";
        });

    legend.append("image")
        .attr("xlink:href", function(d) {
            return d.link;
        })
        .attr("x", w - 72)
        .attr("y", 0)
        .attr("width", 35)
        .attr("height", 35);

    legend.append("text")
        .attr("x", w - 75)
        .attr("y", 15)
        .attr("dy", ".35em")
        .style("text-anchor", "end")
        .text(function(d) {
            return d.name
        });
}