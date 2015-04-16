!
function(window, document, fabric) {
    "use strict";
    function extend(b, a) {
        var prop;
        if (void 0 === b) return a;
        for (prop in a) a.hasOwnProperty(prop) && b.hasOwnProperty(prop) === !1 && (b[prop] = a[prop]);
        return b
    }
    function Picrop(element, options, plugins) {
        return this.init(element, options, plugins)
    }
    function Plugin(Picrop, options) {
        this.Picrop = Picrop,
        this.options = extend(options, this.defaults),
        this.initialize()
    }
    window.Picrop = Picrop,
    Picrop.plugins = [],
    Plugin.prototype = {
        defaults: {},
        initialize: function() {}
    },
    Plugin.extend = function(protoProps) {
        var child, parent = this;
        child = protoProps && protoProps.hasOwnProperty("constructor") ? protoProps.constructor: function() {
            return parent.apply(this, arguments)
        },
        extend(child, parent);
        var Surrogate = function() {
            this.constructor = child
        };
        return Surrogate.prototype = parent.prototype,
        child.prototype = new Surrogate,
        protoProps && extend(child.prototype, protoProps),
        child.__super__ = parent.prototype,
        child
    },
    Picrop.Plugin = Plugin 
    var Canvas = fabric.util.createClass(fabric.Canvas, {});
    Picrop.prototype = {
        defaults: {
            minWidth: null,
            minHeight: null,
            maxWidth: null,
            maxHeight: null,
            plugins: {},
            init: function() {}
        },
        addEventListener: function(eventName, callback) {
            var el = this.canvas.getElement();
            el.addEventListener ? el.addEventListener(eventName, callback) : el.attachEvent && el.attachEvent("on" + eventName, callback)
        },
        dispatchEvent: function(eventName) {
            var event = document.createEvent("Event");
            event.initEvent(eventName, !0, !0),
            this.canvas.getElement().dispatchEvent(event)
        },
        init: function(element, options, plugins) {
            var _this = this;
            if (this.options = extend(options, this.defaults), "string" == typeof element && (element = document.querySelector(element)), null !== element) {
                var plugins = plugins || Picrop.plugins,
                image = new Image;
                image.onload = function() {
                    _this.createFabricImage(element).initDOM(element).initPlugins(plugins),
                    _this.options.init.bind(_this).call()
                },
                image.src = element.src
            }
        },
        initDOM: function(element) {
            var canvas = document.createElement("canvas"),
            canvasContainer = document.createElement("div");
            return canvasContainer.className = "Picrop-image-container",
            canvasContainer.appendChild(canvas),
            this.container = document.createElement("div"),
            this.container.className = "Picrop-container",
            this.container.appendChild(canvasContainer),
            element.parentNode.replaceChild(this.container, element),
            this.canvas = new Canvas(canvas, {
                selection: !1,
                backgroundColor: "#ccc"
            }),
            this.canvas.setWidth(this.image.getWidth()),
            this.canvas.setHeight(this.image.getHeight()),
            this.canvas.add(this.image),
            this.canvas.centerObject(this.image),
            this.image.setCoords(1),
            this
        },
        createFabricImage: function(imgElement) {
            var width = imgElement.width,
            height = imgElement.height,
            scaleMin = 1,
            scaleMax = 1,
            scaleX = 1,
            scaleY = 1;
            null !== this.options.maxWidth && this.options.maxWidth < width && (scaleX = this.options.maxWidth / width),
            null !== this.options.maxHeight && this.options.maxHeight < height && (scaleY = this.options.maxHeight / height),
            scaleMin = Math.min(scaleX, scaleY),
            scaleX = 1,
            scaleY = 1,
            null !== this.options.minWidth && this.options.minWidth > width && (scaleX = this.options.minWidth / width),
            null !== this.options.minHeight && this.options.minHeight > height && (scaleY = this.options.minHeight / height),
            scaleMax = Math.max(scaleX, scaleY);
            var scale = scaleMax * scaleMin;
            return width *= scale,
            height *= scale,
            this.image = new fabric.Image(imgElement, {
                selectable: !1,
                evented: !1,
                lockMovementX: !0,
                lockMovementY: !0,
                lockRotation: !0,
                lockScalingX: !0,
                lockScalingY: !0,
                lockUniScaling: !0,
                hasControls: !1,
                hasBorders: !1
            }),
            this.image.setScaleX(scale),
            this.image.setScaleY(scale),
            this
        },
        initPlugins: function(plugins) {
            this.plugins = {};
            for (var name in plugins) {
                var plugin = plugins[name],
                options = this.options.plugins[name];
                options !== !1 && plugins.hasOwnProperty(name) && (this.plugins[name] = new plugin(this, options))
            }
        },
        getPlugin: function(name) {
            return this.plugins[name]
        },
        selfDestroy: function() {
            var container = this.container,
            image = new Image;
            image.onload = function() {
                container.parentNode.replaceChild(image, container)
            },
            image.src = this.snapshotImage()
        },
        snapshotImage: function() {
            return this.image.toDataURL()
        }
    }
} (window, window.document, fabric),
function(window, document, Picrop, fabric) {
    "use strict";
    var CropZone = fabric.util.createClass(fabric.Rect, {
        _render: function(ctx) {
            this.callSuper("_render", ctx);
            var dashWidth = (ctx.canvas, 7),
            flipX = this.flipX ? -1 : 1,
            flipY = this.flipY ? -1 : 1,
            scaleX = flipX / this.scaleX,
            scaleY = flipY / this.scaleY;
            ctx.scale(scaleX, scaleY),
            ctx.fillStyle = "rgba(0, 0, 0, 0.5)",
            this._renderOverlay(ctx),
            void 0 !== ctx.setLineDash ? ctx.setLineDash([dashWidth, dashWidth]) : void 0 !== ctx.mozDash && (ctx.mozDash = [dashWidth, dashWidth]),
            ctx.strokeStyle = "rgba(0, 0, 0, 0.2)",
            this._renderBorders(ctx),
            this._renderGrid(ctx),
            ctx.lineDashOffset = dashWidth,
            ctx.strokeStyle = "rgba(255, 255, 255, 0.4)",
            this._renderBorders(ctx),
            this._renderGrid(ctx),
            ctx.scale(1 / scaleX, 1 / scaleY)
        },
        _renderOverlay: function(ctx) {
            var canvas = ctx.canvas,
            borderOffset = 0,
            x0 = Math.ceil( - this.getWidth() / 2 - this.getLeft()),
            x1 = Math.ceil( - this.getWidth() / 2),
            x2 = Math.ceil(this.getWidth() / 2),
            x3 = Math.ceil(this.getWidth() / 2 + (canvas.width - this.getWidth() - this.getLeft())),
            y0 = Math.ceil( - this.getHeight() / 2 - this.getTop()),
            y1 = Math.ceil( - this.getHeight() / 2),
            y2 = Math.ceil(this.getHeight() / 2),
            y3 = Math.ceil(this.getHeight() / 2 + (canvas.height - this.getHeight() - this.getTop()));
            ctx.fillRect(x0, y0, x3 - x0, y1 - y0 + borderOffset),
            ctx.fillRect(x0, y1, x1 - x0, y2 - y1 + borderOffset),
            ctx.fillRect(x2, y1, x3 - x2, y2 - y1 + borderOffset),
            ctx.fillRect(x0, y2, x3 - x0, y3 - y2)
        },
        _renderBorders: function(ctx) {
            ctx.beginPath(),
            ctx.moveTo( - this.getWidth() / 2, -this.getHeight() / 2),
            ctx.lineTo(this.getWidth() / 2, -this.getHeight() / 2),
            ctx.lineTo(this.getWidth() / 2, this.getHeight() / 2),
            ctx.lineTo( - this.getWidth() / 2, this.getHeight() / 2),
            ctx.lineTo( - this.getWidth() / 2, -this.getHeight() / 2),
            ctx.stroke()
        },
        _renderGrid: function(ctx) {
            ctx.beginPath(),
            ctx.moveTo( - this.getWidth() / 2 + 1 / 3 * this.getWidth(), -this.getHeight() / 2),
            ctx.lineTo( - this.getWidth() / 2 + 1 / 3 * this.getWidth(), this.getHeight() / 2),
            ctx.stroke(),
            ctx.beginPath(),
            ctx.moveTo( - this.getWidth() / 2 + 2 / 3 * this.getWidth(), -this.getHeight() / 2),
            ctx.lineTo( - this.getWidth() / 2 + 2 / 3 * this.getWidth(), this.getHeight() / 2),
            ctx.stroke(),
            ctx.beginPath(),
            ctx.moveTo( - this.getWidth() / 2, -this.getHeight() / 2 + 1 / 3 * this.getHeight()),
            ctx.lineTo(this.getWidth() / 2, -this.getHeight() / 2 + 1 / 3 * this.getHeight()),
            ctx.stroke(),
            ctx.beginPath(),
            ctx.moveTo( - this.getWidth() / 2, -this.getHeight() / 2 + 2 / 3 * this.getHeight()),
            ctx.lineTo(this.getWidth() / 2, -this.getHeight() / 2 + 2 / 3 * this.getHeight()),
            ctx.stroke()
        }
    });
    Picrop.plugins.crop = Picrop.Plugin.extend({
        startX: null,
        startY: null,
        isKeyCroping: !1,
        isKeyLeft: !1,
        isKeyUp: !1,
        defaults: {
            minHeight: 1,
            minWidth: 1,
            ratio: null,
            quickCropKey: !1
        },
        initialize: function() {
            this.Picrop.canvas.on("mouse:down", this.onMouseDown.bind(this)),
            this.Picrop.canvas.on("mouse:move", this.onMouseMove.bind(this)),
            this.Picrop.canvas.on("mouse:up", this.onMouseUp.bind(this)),
            this.Picrop.canvas.on("object:moving", this.onObjectMoving.bind(this)),
            this.Picrop.canvas.on("object:scaling", this.onObjectScaling.bind(this)),
            fabric.util.addListener(fabric.document, "keydown", this.onKeyDown.bind(this)),
            fabric.util.addListener(fabric.document, "keyup", this.onKeyUp.bind(this)),
            this.Picrop.addEventListener("image:change", this.releaseFocus.bind(this))
        },
        onObjectMoving: function(event) {
            if (this.hasFocus()) {
                var currentObject = event.target;
                if (currentObject === this.cropZone) {
                    var canvas = this.Picrop.canvas,
                    x = currentObject.getLeft(),
                    y = currentObject.getTop(),
                    w = currentObject.getWidth(),
                    h = currentObject.getHeight(),
                    maxX = canvas.getWidth() - w,
                    maxY = canvas.getHeight() - h;
                    0 > x && currentObject.set("left", 0),
                    0 > y && currentObject.set("top", 0),
                    x > maxX && currentObject.set("left", maxX),
                    y > maxY && currentObject.set("top", maxY),
                    this.Picrop.dispatchEvent("crop:update")
                }
            }
        },
        onObjectScaling: function(event) {
            if (this.hasFocus()) {
                var preventScaling = !1,
                currentObject = event.target;
                if (currentObject === this.cropZone) {
                    var canvas = this.Picrop.canvas,
                    pointer = canvas.getPointer(event.e),
                    minX = (pointer.x, pointer.y, currentObject.getLeft()),
                    minY = currentObject.getTop(),
                    maxX = currentObject.getLeft() + currentObject.getWidth(),
                    maxY = currentObject.getTop() + currentObject.getHeight();
                    if (null !== this.options.ratio && (0 > minX || maxX > canvas.getWidth() || 0 > minY || maxY > canvas.getHeight()) && (preventScaling = !0), 0 > minX || maxX > canvas.getWidth() || preventScaling) {
                        var lastScaleX = this.lastScaleX || 1;
                        currentObject.setScaleX(lastScaleX)
                    }
                    if (0 > minX && currentObject.setLeft(0), 0 > minY || maxY > canvas.getHeight() || preventScaling) {
                        var lastScaleY = this.lastScaleY || 1;
                        currentObject.setScaleY(lastScaleY)
                    }
                    0 > minY && currentObject.setTop(0),
                    currentObject.getWidth() < this.options.minWidth && currentObject.scaleToWidth(this.options.minWidth),
                    currentObject.getHeight() < this.options.minHeight && currentObject.scaleToHeight(this.options.minHeight),
                    this.lastScaleX = currentObject.getScaleX(),
                    this.lastScaleY = currentObject.getScaleY(),
                    this.Picrop.dispatchEvent("crop:update")
                }
            }
        },
        onMouseDown: function(event) {
            if (this.hasFocus()) {
                var canvas = this.Picrop.canvas;
                canvas.calcOffset();
                var pointer = canvas.getPointer(event.e),
                x = pointer.x,
                y = pointer.y,
                point = new fabric.Point(x, y),
                activeObject = canvas.getActiveObject();
                activeObject === this.cropZone || this.cropZone.containsPoint(point) || (canvas.discardActiveObject(), this.cropZone.setWidth(0), this.cropZone.setHeight(0), this.cropZone.setScaleX(1), this.cropZone.setScaleY(1), this.startX = x, this.startY = y)
            }
        },
        onMouseMove: function(event) {
            if (this.isKeyCroping) return this.onMouseMoveKeyCrop(event);
            if (null !== this.startX && null !== this.startY) {
                var canvas = this.Picrop.canvas,
                pointer = canvas.getPointer(event.e),
                x = pointer.x,
                y = pointer.y;
                this._renderCropZone(this.startX, this.startY, x, y)
            }
        },
        onMouseMoveKeyCrop: function(event) {
            var canvas = this.Picrop.canvas,
            zone = this.cropZone,
            pointer = canvas.getPointer(event.e),
            x = pointer.x,
            y = pointer.y;
            zone.left && zone.top || (zone.setTop(y), zone.setLeft(x)),
            this.isKeyLeft = x < zone.left + zone.width / 2,
            this.isKeyUp = y < zone.top + zone.height / 2,
            this._renderCropZone(Math.min(zone.left, x), Math.min(zone.top, y), Math.max(zone.left + zone.width, x), Math.max(zone.top + zone.height, y))
        },
        onMouseUp: function() {
            if (null !== this.startX && null !== this.startY) {
                var canvas = this.Picrop.canvas;
                this.cropZone.setCoords(),
                canvas.setActiveObject(this.cropZone),
                canvas.calcOffset(),
                this.startX = null,
                this.startY = null
            }
        },
        onKeyDown: function(event) { ! 1 === this.options.quickCropKey || event.keyCode !== this.options.quickCropKey || this.isKeyCroping || (this.isKeyCroping = !0, this.Picrop.canvas.discardActiveObject(), this.cropZone.setWidth(0), this.cropZone.setHeight(0), this.cropZone.setScaleX(1), this.cropZone.setScaleY(1), this.cropZone.setTop(0), this.cropZone.setLeft(0))
        },
        onKeyUp: function(event) { ! 1 !== this.options.quickCropKey && event.keyCode === this.options.quickCropKey && this.isKeyCroping && (this.isKeyCroping = !1, this.startX = 1, this.startY = 1, this.onMouseUp())
        },
        selectZone: function(x, y, width, height, forceDimension) {
            this.hasFocus() || this.requireFocus(),
            forceDimension ? this.cropZone.set({
                left: x,
                top: y,
                width: width,
                height: height
            }) : this._renderCropZone(x, y, x + width, y + height);
            var canvas = this.Picrop.canvas;
            canvas.bringToFront(this.cropZone),
            this.cropZone.setCoords(),
            canvas.setActiveObject(this.cropZone),
            canvas.calcOffset(),
            this.Picrop.dispatchEvent("crop:update")
        },
        toggleCrop: function() {
            this.hasFocus() ? this.releaseFocus() : this.requireFocus()
        },
        cropCurrentZone: function() {
            if (this.hasFocus() && !(this.cropZone.width < 1 && this.cropZone.height < 1)) {
                var _this = this,
                Picrop = this.Picrop,
                canvas = Picrop.canvas;
                this.cropZone.visible = !1;
                var image = new Image;
                image.onload = function() {
                    if (! (this.height < 1 || this.width < 1)) {
                        var imgInstance = new fabric.Image(this, {
                            selectable: !1,
                            evented: !1,
                            lockMovementX: !0,
                            lockMovementY: !0,
                            lockRotation: !0,
                            lockScalingX: !0,
                            lockScalingY: !0,
                            lockUniScaling: !0,
                            hasControls: !1,
                            hasBorders: !1
                        }),
                        width = this.width,
                        height = this.height;
                        canvas.setWidth(width),
                        canvas.setHeight(height),
                        _this.Picrop.image.remove(),
                        _this.Picrop.image = imgInstance,
                        canvas.add(imgInstance),
                        Picrop.dispatchEvent("image:change")
                    }
                },
                image.src = canvas.toDataURL({
                    left: this.cropZone.getLeft(),
                    top: this.cropZone.getTop(),
                    width: this.cropZone.getWidth(),
                    height: this.cropZone.getHeight()
                })
            }
        },
        hasFocus: function() {
            return void 0 !== this.cropZone
        },
        requireFocus: function() {
            this.cropZone = new CropZone({
                fill: "transparent",
                hasBorders: !1,
                originX: "left",
                originY: "top",
                cornerColor: "#444",
                cornerSize: 8,
                transparentCorners: !1,
                lockRotation: !0,
                hasRotatingPoint: !1
            }),
            null !== this.options.ratio && this.cropZone.set("lockUniScaling", !0),
            this.Picrop.canvas.add(this.cropZone),
            this.Picrop.canvas.defaultCursor = "crosshair" 
        },
        releaseFocus: function() {
            void 0 !== this.cropZone && (this.cropZone.remove(), this.cropZone = void 0, this.cropButton.active(!1), this.okButton.hide(!0), this.cancelButton.hide(!0), this.Picrop.canvas.defaultCursor = "default", this.Picrop.dispatchEvent("crop:update"))
        },
        _renderCropZone: function(fromX, fromY, toX, toY) {
            var canvas = this.Picrop.canvas,
            isRight = toX > fromX,
            isLeft = !isRight,
            isDown = toY > fromY,
            isUp = !isDown,
            minWidth = Math.min( + this.options.minWidth, canvas.getWidth()),
            minHeight = Math.min( + this.options.minHeight, canvas.getHeight()),
            leftX = Math.min(fromX, toX),
            rightX = Math.max(fromX, toX),
            topY = Math.min(fromY, toY),
            bottomY = Math.max(fromY, toY);
            leftX = Math.max(0, leftX),
            rightX = Math.min(canvas.getWidth(), rightX),
            topY = Math.max(0, topY),
            bottomY = Math.min(canvas.getHeight(), bottomY),
            minWidth > rightX - leftX && (isRight ? rightX = leftX + minWidth: leftX = rightX - minWidth),
            minHeight > bottomY - topY && (isDown ? bottomY = topY + minHeight: topY = bottomY - minHeight),
            0 > leftX && (rightX += Math.abs(leftX), leftX = 0),
            rightX > canvas.getWidth() && (leftX -= rightX - canvas.getWidth(), rightX = canvas.getWidth()),
            0 > topY && (bottomY += Math.abs(topY), topY = 0),
            bottomY > canvas.getHeight() && (topY -= bottomY - canvas.getHeight(), bottomY = canvas.getHeight());
            var width = rightX - leftX,
            height = bottomY - topY,
            currentRatio = width / height;
            if (this.options.ratio && +this.options.ratio !== currentRatio) {
                var ratio = +this.options.ratio;
                if (this.isKeyCroping && (isLeft = this.isKeyLeft, isUp = this.isKeyUp), ratio > currentRatio) {
                    var newWidth = height * ratio;
                    isLeft && (leftX -= newWidth - width),
                    width = newWidth
                } else if (currentRatio > ratio) {
                    var newHeight = height / (ratio * height / width);
                    isUp && (topY -= newHeight - height),
                    height = newHeight
                }
                if (0 > leftX && (leftX = 0), 0 > topY && (topY = 0), leftX + width > canvas.getWidth()) {
                    var newWidth = canvas.getWidth() - leftX;
                    height = newWidth * height / width,
                    width = newWidth,
                    isUp && (topY = fromY - height)
                }
                if (topY + height > canvas.getHeight()) {
                    var newHeight = canvas.getHeight() - topY;
                    width = width * newHeight / height,
                    height = newHeight,
                    isLeft && (leftX = fromX - width)
                }
            }
            this.cropZone.left = leftX,
            this.cropZone.top = topY,
            this.cropZone.width = width,
            this.cropZone.height = height,
            this.Picrop.canvas.bringToFront(this.cropZone),
            this.Picrop.dispatchEvent("crop:update")
        }
    })
} (window, document, Picrop, fabric);
//# sourceMappingURL=Picrop.min.js.map
