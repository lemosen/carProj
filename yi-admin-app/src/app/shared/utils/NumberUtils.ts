const _1zero = "0";
const _2zero = "00";
const _3zero = "000";
const _4zero = "0000";

const _decimalSymbol = ".";
const _groupingSymbol = ",";
const _negativeSymbol = "-";
const _currencySymbol = "$";

export function getNumberFormatString(precision: number) {
    let fmt = "0";
    if (precision > 0) {
        fmt += "." + getZeroString(precision);
    }
    return fmt;
}

export function isNumber(object) {
    if (object == null) return false;

    if (typeof object != "number") return false;

    // it's a number, now check if it's a valid number
    return !isNaN(object) &&
        object != Number.POSITIVE_INFINITY &&
        object != Number.NEGATIVE_INFINITY;
}

export function getZeroString(length) {
    if (length <= 0) return;

    let pad;
    // with > 4 zeros (very rare), build up a leading pad 4 0's at a time
    while (length > 4) {
        if (pad == null) pad = _4zero;
        else pad += _4zero;
        length -= 4;
    }

    let finalPad;
    switch (length) {
        case 4:
            finalPad = _4zero;
            break;
        case 3:
            finalPad = _3zero;
            break;
        case 2:
            finalPad = _2zero;
            break;
        case 1:
            finalPad = _1zero;
            break;
    }

    // no leading pad (less than 4 zeros total)
    if (pad == null) return finalPad;
    return pad + finalPad;
}

export function toFixed(n) {
    let nStr = n.toFixed(),
        k = nStr.lastIndexOf("e");
    if (k == -1) {
        return nStr;
    } else {

        let lastPow = parseInt(nStr.substr(k + 1), 10),
            numDigits = 1 + lastPow,
            str = "",
            sum = 0;
        do {
            let digit = parseInt(nStr, 10),
                pow = parseInt(nStr.substr(k + 1), 10);
            if (lastPow > pow + 1) {
                str = str + getZeroString(lastPow - pow - 1) + digit;
            } else {
                str = str + digit;
            }
            sum += digit * Math.pow(10, pow);
            nStr = (n - sum).toFixed();
            k = nStr.lastIndexOf("e");
            lastPow = pow;
        } while (k != -1);

        let l = numDigits - str.length - nStr.length;
        if (l > 0) {
            return str + getZeroString(l) + nStr;

        } else {
            return str + nStr;
        }
    }
}

export function formatNumber(number: any, decimalPrecision?: number,
                             decimalSymbol?: string, groupingSymbol?: string, negativeSymbol?: string,
                             minInteger?: number, maxFraction?: number, minPrecision?: number, maxPrecision?: number) {
    if (!isNumber(number)) return "";

    let negative = (number < 0),
        wholeString = null,
        decimalString = null;
    if (negative) {
        number = -number;
    }

    let bySignificantDigits = (minPrecision != null || maxPrecision != null),
        minFraction = 0;

    if (bySignificantDigits) {
        // `minPrecision` must be an integer between 1 and 21.
        minPrecision = Math.max(1, Math.min(21, minPrecision || 0));
        // `maxPrecision` must be an integer between `minPrecision` and 21.
        maxPrecision = Math.max(minPrecision, Math.min(21, maxPrecision || 0));

    } else {
        // `minInteger` must be an integer between 1 and 21.
        minInteger = Math.max(1, Math.min(21, minInteger || 0));
        // `minFraction` must be an integer between 0 and 20.
        minFraction = Math.max(0, Math.min(20, decimalPrecision || 0));
        // `maxFraction` must be an integer between `minFraction` and 20.
        maxFraction = Math.max(minFraction, Math.min(20, maxFraction || 0));
    }

    let zeroStr = getZeroString(1);
    if (bySignificantDigits) {
        let p = maxPrecision, e = 0, m;

        if (number == 0) {
            e = 0;
            m = getZeroString(p);
            if (e == p - 1) {
                wholeString = m;
            }
        } else {

            e = Math.floor(Math.log(number) / Math.LN10);
            let n = 0;
            if (e < p) {
                n = Math.round(number * Math.pow(10, -(e - p + 1)));
            } else {
                n = Math.round(number / Math.pow(10, e - p + 1));
            }
            if (n == Math.pow(10, p)) {
                ++e;
                n /= 10;
            }

            if (e >= p) {

                wholeString = (
                    toFixed(n) + getZeroString(e - p + 1));
            } else if (e == p - 1) {
                wholeString = toFixed(n);
            } else {
                m = toFixed(n);
            }
        }

        if (wholeString == null) {
            if (e >= 0) {
                wholeString = m.substr(0, e + 1);
                decimalString = m.substr(e + 1, p - (e + 1));
            } else {
                wholeString = zeroStr;
                decimalString = getZeroString(-(e + 1)) + m;
            }

            let cut0 = maxPrecision - minPrecision,
                cut = cut0;
            while (cut > 0) {
                if (decimalString.charAt(p - (e + 1) - 1 - (cut0 - cut)) == zeroStr) {
                    --cut;
                } else {
                    break;
                }
            }
            if (p - (e + 1) == cut0 - cut) {
                decimalString = null;
            } else {
                decimalString = decimalString.substr(0, p - (e + 1) - (cut0 - cut));
            }
        }

    } else {
        let f = maxFraction,
            n = Math.round(number * Math.pow(10, f)),
            m = (n == 0 ? zeroStr : toFixed(n)),
            l = 0;
        if (f > 0) {
            let k = m.length;
            if (k <= f) {
                m = getZeroString(f + 1 - k) + m;
                k = f + 1;
            }
            wholeString = m.substr(0, k - f);
            decimalString = m.substr(k - f, f);
            l = k - f;

            let cut0 = maxFraction - minFraction,
                cut = cut0;
            while (cut > 0) {
                if (decimalString.charAt(f - 1 - (cut0 - cut)) == zeroStr) {
                    --cut;
                } else {
                    break;
                }
            }
            if (f == cut0 - cut) {
                decimalString = null;
            } else {
                decimalString = decimalString.substr(0, f - (cut0 - cut));
            }
        } else {
            wholeString = m;
            l = m.length;
        }
        if (l < minInteger) {
            wholeString = getZeroString(minInteger - l) + wholeString;
        }
    }

    let wholeLength = wholeString.length,
        r = wholeLength % 3,
        // `tripletCount` is the number of complete chunks of 3 digits.
        tripletCount = (wholeLength - r) / 3,
        beforeTripletLength = (negative ? 1 : 0) + (r != 0 ? 1 : 0),
        numGroupSymbols = (r != 0 ? 1 : 0) + tripletCount - 1,
        templateLength = (
            beforeTripletLength +
            numGroupSymbols +
            tripletCount +
            (decimalString != null ? 2 : 0)),
        template = new Array(templateLength);

    let k = 0;
    if (negative) {
        template[k++] = (negativeSymbol || _negativeSymbol);
    }

    // Whole part - slice it into chunks joined with grouping symbols.
    groupingSymbol = groupingSymbol || _groupingSymbol;
    let notFirstGroup = false;
    if (r != 0) {
        // Start with the incomplete chunk (first 1 or 2 digits), if any, ...
        template[k++] = wholeString.substr(0, r);
        notFirstGroup = true;
    }
    for (let i = 0, j = r; i < tripletCount; ++i, j += 3, notFirstGroup = true) {
        if (notFirstGroup) {
            template[k++] = groupingSymbol;
        }
        // ... then slice out each chunk of 3 digits.
        template[k++] = wholeString.substr(j, 3);
    }

    // Append the decimal part.
    if (decimalString != null) {
        template[k++] = (decimalSymbol || _decimalSymbol);
        template[k] = decimalString;
    }

    // Assembly - join the chunks of the whole part with grouping symbols, and glue together
    // the whole part, decimal symbol, decimal part, and negative sign as appropriate.
    return template.join("");
}
