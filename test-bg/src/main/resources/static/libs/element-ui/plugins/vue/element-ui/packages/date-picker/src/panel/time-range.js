/**
 * Created by Zhou on 17/2/9.
 */
define(function(require, exports, module){
    const parseDate = require('../util/index').parseDate;
    const limitRange = require('../util/index').limitRange;

    const Locale = require('../../../../src/mixins/locale');
    const TimeSpinner = require('../basic/time-spinner');

    const MIN_TIME = parseDate('00:00:00', 'HH:mm:ss');
    const MAX_TIME = parseDate('23:59:59', 'HH:mm:ss');
    const isDisabled = function(minTime, maxTime) {
        const minValue = minTime.getHours() * 3600 + minTime.getMinutes() * 60 + minTime.getSeconds();
        const maxValue = maxTime.getHours() * 3600 + maxTime.getMinutes() * 60 + maxTime.getSeconds();

        return minValue > maxValue;
    };
    const clacTime = function(time) {
        time = Array.isArray(time) ? time : [time];
        const minTime = time[0] || new Date();
        const date = new Date();
        date.setHours(date.getHours() + 1);
        const maxTime = time[1] || date;

        if (minTime > maxTime) return clacTime();
        return { minTime, maxTime };
    };

    module.exports =  {
        mixins: [Locale],
        template:require('./time-range.tpl'),
        components: { TimeSpinner },

        computed: {
            showSeconds() {
                return (this.format || '').indexOf('ss') !== -1;
            }
        },

        props: ['value'],

        data() {
            const time = clacTime(this.$options.defaultValue);

            return {
                popperClass: '',
                minTime: time.minTime,
                maxTime: time.maxTime,
                btnDisabled: isDisabled(time.minTime, time.maxTime),
                maxHours: time.maxTime.getHours(),
                maxMinutes: time.maxTime.getMinutes(),
                maxSeconds: time.maxTime.getSeconds(),
                minHours: time.minTime.getHours(),
                minMinutes: time.minTime.getMinutes(),
                minSeconds: time.minTime.getSeconds(),
                format: 'HH:mm:ss',
                visible: false,
                width: 0
            };
        },

        watch: {
            value(newVal) {
                var that = this;
                this.panelCreated();
                this.$nextTick(function(_){that.ajustScrollTop()});
            }
        },

        methods: {
            panelCreated() {
                const time = clacTime(this.value);
                if (time.minTime === this.minTime && time.maxTime === this.maxTime) {
                    return;
                }

                this.handleMinChange({
                    hours: time.minTime.getHours(),
                    minutes: time.minTime.getMinutes(),
                    seconds: time.minTime.getSeconds()
                });
                this.handleMaxChange({
                    hours: time.maxTime.getHours(),
                    minutes: time.maxTime.getMinutes(),
                    seconds: time.maxTime.getSeconds()
                });
            },

            handleClear() {
                this.handleCancel();
            },

            handleCancel() {
                this.$emit('pick');
            },

            handleChange() {
                if (this.minTime > this.maxTime) return;
                this.$refs.minSpinner.selectableRange = [[MIN_TIME, this.maxTime]];
                this.$refs.maxSpinner.selectableRange = [[this.minTime, MAX_TIME]];
                this.handleConfirm(true);
            },

            handleMaxChange(date) {
                if (date.hours !== undefined) {
                    this.maxTime.setHours(date.hours);
                    this.maxHours = this.maxTime.getHours();
                }
                if (date.minutes !== undefined) {
                    this.maxTime.setMinutes(date.minutes);
                    this.maxMinutes = this.maxTime.getMinutes();
                }
                if (date.seconds !== undefined) {
                    this.maxTime.setSeconds(date.seconds);
                    this.maxSeconds = this.maxTime.getSeconds();
                }
                this.handleChange();
            },

            handleMinChange(date) {
                if (date.hours !== undefined) {
                    this.minTime.setHours(date.hours);
                    this.minHours = this.minTime.getHours();
                }
                if (date.minutes !== undefined) {
                    this.minTime.setMinutes(date.minutes);
                    this.minMinutes = this.minTime.getMinutes();
                }
                if (date.seconds !== undefined) {
                    this.minTime.setSeconds(date.seconds);
                    this.minSeconds = this.minTime.getSeconds();
                }

                this.handleChange();
            },

            setMinSelectionRange(start, end) {
                this.$emit('select-range', start, end);
            },

            setMaxSelectionRange(start, end) {
                this.$emit('select-range', start + 11, end + 11);
            },

            handleConfirm(visible = false, first = false) {
                const minSelectableRange = this.$refs.minSpinner.selectableRange;
                const maxSelectableRange = this.$refs.maxSpinner.selectableRange;

                this.minTime = limitRange(this.minTime, minSelectableRange);
                this.maxTime = limitRange(this.maxTime, maxSelectableRange);

                if (first) return;
                this.$emit('pick', [this.minTime, this.maxTime], visible, first);
            },

            ajustScrollTop() {
                this.$refs.minSpinner.ajustScrollTop();
                this.$refs.maxSpinner.ajustScrollTop();
            }
        },

        mounted() {
            var that = this;
            this.$nextTick(function(){that.handleConfirm(true, true)});
        }
    };

})