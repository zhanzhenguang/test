define(function (require,exports,module) {
    const throttle = require('throttle-debounce').throttle;
    const debounce = require('throttle-debounce').debounce;
    const addResizeListener = require('../../src/utils/resize-event').addResizeListener;
    const removeResizeListener = require('../../src/utils/resize-event').removeResizeListener;

    module.exports = {
        name: 'ElCarousel',
        template:require('./carousel.tpl'),
        props: {
            initialIndex: {
                type: Number,
                default: 0
            },
            height: String,
            trigger: {
                type: String,
                default: 'hover'
            },
            autoplay: {
                type: Boolean,
                default: true
            },
            interval: {
                type: Number,
                default: 3000
            },
            indicatorPosition: String,
            indicator: {
                type: Boolean,
                default: true
            },
            arrow: {
                type: String,
                default: 'hover'
            },
            type: String
        },

        data() {
            return {
                items: [],
                activeIndex: -1,
                containerWidth: 0,
                timer: null,
                hover: false
            };
        },

        watch: {
            activeIndex(val, oldVal) {
                this.resetItemPosition();
                this.$emit('change', val, oldVal);
            }
        },

        methods: {
            handleMouseEnter() {
                this.hover = true;
                this.pauseTimer();
            },

            handleMouseLeave() {
                this.hover = false;
                this.startTimer();
            },

            itemInStage(item, index) {
                const length = this.items.length;
                if (index === length - 1 && item.inStage && this.items[0].active ||
                    (item.inStage && this.items[index + 1] && this.items[index + 1].active)) {
                    return 'left';
                } else if (index === 0 && item.inStage && this.items[length - 1].active ||
                    (item.inStage && this.items[index - 1] && this.items[index - 1].active)) {
                    return 'right';
                }
                return false;
            },

            handleButtonEnter(arrow) {
                this.items.forEach((item, index) => {
                    if (arrow === this.itemInStage(item, index)) {
                    item.hover = true;
                }
            });
            },

            handleButtonLeave() {
                this.items.forEach(item => {
                    item.hover = false;
            });
            },

            handleItemChange() {
                debounce(100, () => {
                    this.updateItems();
            });
            },

            updateItems() {
                this.items = this.$children.filter(child => child.$options.name === 'ElCarouselItem');
            },

            resetItemPosition() {
                this.items.forEach((item, index) => {
                    item.translateItem(index, this.activeIndex);
            });
            },

            playSlides() {
                if (this.activeIndex < this.items.length - 1) {
                    this.activeIndex++;
                } else {
                    this.activeIndex = 0;
                }
            },

            pauseTimer() {
                clearInterval(this.timer);
            },

            startTimer() {
                if (this.interval <= 0 || !this.autoplay) return;
                this.timer = setInterval(this.playSlides, this.interval);
            },

            setActiveItem(index) {
                if (typeof index === 'string') {
                    const filteredItems = this.items.filter(item => item.name === index);
                    if (filteredItems.length > 0) {
                        index = this.items.indexOf(filteredItems[0]);
                    }
                }
                index = Number(index);
                if (isNaN(index) || index !== Math.floor(index)) {
                    process.env.NODE_ENV !== 'production' &&
                    console.warn('[Element Warn][Carousel]index must be an integer.');
                    return;
                }
                let length = this.items.length;
                if (index < 0) {
                    this.activeIndex = length - 1;
                } else if (index >= length) {
                    this.activeIndex = 0;
                } else {
                    this.activeIndex = index;
                }
            },

            prev() {
                this.setActiveItem(this.activeIndex - 1);
            },

            next() {
                this.setActiveItem(this.activeIndex + 1);
            },

            handleIndicatorClick(index) {
                this.activeIndex = index;
            },

            handleIndicatorHover(index) {
                if (this.trigger === 'hover' && index !== this.activeIndex) {
                    this.activeIndex = index;
                }
            }
        },

        created() {
            this.throttledArrowClick = throttle(300, true, index => {
                    this.setActiveItem(index);
        });
            this.throttledIndicatorHover = throttle(300, index => {
                    this.handleIndicatorHover(index);
        });
        },

        mounted() {
            this.updateItems();
            this.$nextTick(() => {
                addResizeListener(this.$el, this.resetItemPosition);
            if (this.initialIndex < this.items.length && this.initialIndex >= 0) {
                this.activeIndex = this.initialIndex;
            }
            this.startTimer();
        });
        },

        beforeDestroy() {
            if (this.$el) removeResizeListener(this.$el, this.resetItemPosition);
        }
    };
})