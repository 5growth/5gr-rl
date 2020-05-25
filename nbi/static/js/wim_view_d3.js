function representation(rawData){
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
var chartDiv = document.getElementById("chartd3");
var h = 500,
    w = chartDiv.clientWidth,
    fill = d3.scale.category20();

// console.log(w, h, chartDiv.clientWidth, chartDiv.clientHeight, chartDiv);

var vis = d3.select("#chartd3")
    .append("svg")
    .attr("width", "100%")
    .attr("height", h)
    //.call(d3.behavior.zoom()
    //    .scaleExtent([1, 3])
    //    .center([w / 2, h / 2])
    //    .on("zoom", function (d) {
    //        console.log("test");
    //        //vis.attr("transform", "scale(" + d3.event.scale + ")");
    //        image.attr("transform", "scale(" + d3.event.scale + ")");
    //        path.attr("transform", "scale(" + d3.event.scale + ")");
    //        text.attr("transform", "scale(" + d3.event.scale + ")");
    //    }))
    ;

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



var path = vis.append("svg:g").selectAll("path")
    .data(force.links())
    .enter().append("svg:path")
    .style("stroke-width", 3)
    .style("stroke", "gray")
    .style("fill", "none")
    .attr("class", function(d) { return "link " + d.type; })
    .attr("marker-mid", function(d) { return "url(#" + d.type + ")"; });


var node = vis.selectAll(".node")
    .data(rawData.nodes)
    .enter().append("g")
    .attr("class", "node")
    .on("dblclick", function(d, i) {
        alert('Name: \n' + d.name);
    })
    .call(force.drag);

node.append("image")
    .attr("xlink:href", function(d) {
        return d.img;
    })
    .attr("x", -15)
    .attr("y", -15)
    .attr("width", 30)
    .attr("height", 30);

var text = vis.selectAll(".text")
    .data(rawData.nodes)
    .enter().append("text")
    //.style("font", "12px helvetica")
    .attr("x", function(d) {
        return d.x;
    })
    .attr("y", function(d) {
        return d.y - 11;
    })
    .text(function(d) {
        return d.name;
    })
    .call(force.drag);

vis.style("opacity", 1e-6)
    .transition()
    .duration(1000)
    .style("opacity", 1)
    .style("align", "center")
    .style("border", 2);

force.on("tick", function() {
    path.attr("d", function(d) {
        var dx = d.target.x - d.source.x,
        dy = d.target.y - d.source.y,
        dr = Math.sqrt(dx * dx + dy * dy);
        return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;

    });

    node.attr("transform", function(d) {
        return "translate(" + d.x + "," + d.y + ")";
    });

    text.attr("x", function(d) {
            return d.x;
        })
        .attr("y", function(d) {
            return d.y - 11;
        });
});

//Legend
var legend = vis.selectAll(".legend")
    .data([{
        "name": "GW/PE",
        "link": "/static/images/router.png"
    }, {
        "name": "OF",
        "link": "/static/images/switch.png"
    }, {
        "name": "GMPLS",
        "link": "/static/images/oxc.png"
    }])
    .enter().append("g")
    .attr("class", "legend")
    .attr("transform", function(d, i) {
        return "translate(0," + i * 30 + ")";
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
    .attr("x", w - 74)
    .attr("y", 15)
    .attr("dy", ".35em")
    .style("text-anchor", "end")
    .text(function(d) {
        return d.name
    });
}