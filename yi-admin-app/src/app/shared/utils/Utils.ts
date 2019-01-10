import {ObjectUtils} from "./ObjectUtils";

/**
 * 在下一个渲染周期执行回调函数
 * @param cb
 */
export function nextTick(cb) {
    setTimeout(cb, 0);
}

export class Utils {
    /**
     * 在下一个渲染周期执行回调函数
     * @param cb
     */
    public static nextTick(cb) {
        setTimeout(cb, 0);
    }

    /**
     * 将给定的内容转换为数值
     * @param val
     * @returns {number}
     */
    public static toNumber(val): number {
        return Number(val);
    }

    public static filterArrayByString(mainArr, searchText) {
        if (searchText === '') {
            return mainArr;
        }

        searchText = searchText.toLowerCase();

        return mainArr.filter(itemObj => {
            return this.searchInObj(itemObj, searchText);
        });
    }

    public static searchInObj(itemObj, searchText) {
        for (const prop in itemObj) {
            if (!itemObj.hasOwnProperty(prop)) {
                continue;
            }

            const value = itemObj[prop];

            if (typeof value === 'string') {
                if (this.searchInString(value, searchText)) {
                    return true;
                }
            }

            else if (Array.isArray(value)) {
                if (this.searchInArray(value, searchText)) {
                    return true;
                }
            }

            if (typeof value === 'object') {
                if (this.searchInObj(value, searchText)) {
                    return true;
                }
            }
        }
    }

    public static searchInArray(arr, searchText) {
        for (const value of arr) {
            if (typeof value === 'string') {
                if (this.searchInString(value, searchText)) {
                    return true;
                }
            }

            if (typeof value === 'object') {
                if (this.searchInObj(value, searchText)) {
                    return true;
                }
            }
        }
    }

    public static searchInString(value, searchText) {
        return value.toLowerCase().includes(searchText);
    }

    public static generateGUID() {
        function S4() {
            return Math.floor((1 + Math.random()) * 0x10000)
                .toString(16)
                .substring(1);
        }

        return S4() + S4();
    }

    public static toggleInArray(item, array) {
        if (array.indexOf(item) === -1) {
            array.push(item);
        } else {
            array.splice(array.indexOf(item), 1);
        }
    }

    public static toggleInArrayByField(array, key) {
        var result = [array[0]];
        for (var i = 1; i < array.length; i++) {
            var item = array[i];
            var repeat = false;
            for (var j = 0; j < result.length; j++) {
                if (item[key] == result[j][key]) {
                    repeat = true;
                    break;
                }
            }
            if (!repeat) {
                result.push(item);
            }
        }
        return result;
    }

    public static removeItemInArray(item, array) {
        let index = array.indexOf(item);
        if (index >= 0) {
            array.splice(index, 1);
        }
    }

    /***
     * 将数组2中的元素添加至数组1中并祛除重复元素
     * @param arr1
     * @param arr2
     * @param key
     */
    public static dislodgeDuplicateInArray(arr1, arr2, key): Array<any> {
        if (ObjectUtils.isEmpty(arr1)) {
            arr1 = new Array<any>();
        }
        arr2.map(item => {
            arr1.push(item)
        });
        arr1 = Utils.toggleInArrayByField(arr1, key);
        return arr1;
    }

    public static handleize(text) {
        return text.toString().toLowerCase()
            .replace(/\s+/g, '-')           // Replace spaces with -
            .replace(/[^\w\-]+/g, '')       // Remove all non-word chars
            .replace(/\-\-+/g, '-')         // Replace multiple - with single -
            .replace(/^-+/, '')             // Trim - from start of text
            .replace(/-+$/, '');            // Trim - from end of text
    }

    public static toggleShowOrHidden() {

    }
}
