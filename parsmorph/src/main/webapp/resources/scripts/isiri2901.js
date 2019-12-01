/*
 * Copyright 2012 Vahid Mavaji
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

var isiri2901_lang = 1;        // 1: Persian, 0: English
var isiri2901_nativelang = 0;  // 1: Persian, 0: English

// Persian keyboard map based on ISIRI-2901

var isirikey = [
  0x0020, 0x0021, 0x061B, 0x066B, 0xFDFC, 0x066A, 0x060C, 0x06AF,
  0x0029, 0x0028, 0x002A, 0x002B, 0x0648, 0x002D, 0x002E, 0x002F,
  0x06F0, 0x06F1, 0x06F2, 0x06F3, 0x06F4, 0x06F5, 0x06F6, 0x06F7,
  0x06F8, 0x06F9, 0x003A, 0x06A9, 0x003E, 0x003D, 0x003C, 0x061F,
  0x066C, 0x0624, 0x200C, 0x0698, 0x064A, 0x064D, 0x0625, 0x0623,
  0x0622, 0x0651, 0x0629, 0x00BB, 0x00AB, 0x0621, 0x0654, 0x005D,
  0x005B, 0x0652, 0x064B, 0x0626, 0x064F, 0x064E, 0x0670, 0x064C,
  0x0653, 0x0650, 0x0643, 0x062C, 0x005C, 0x0686, 0x00D7, 0x0640,
  0x200D, 0x0634, 0x0630, 0x0632, 0x06CC, 0x062B, 0x0628, 0x0644,
  0x0627, 0x0647, 0x062A, 0x0646, 0x0645, 0x067E, 0x062F, 0x062E,
  0x062D, 0x0636, 0x0642, 0x0633, 0x0641, 0x0639, 0x0631, 0x0635,
  0x0637, 0x063A, 0x0638, 0x007D, 0x007C, 0x007B, 0x007E
];

// on Alt+Ctrl+anything, switch language
function PersianKeyDown(e)
{
  if (window.event)
    e = window.event;
  if (e.ctrlKey && e.altKey) {
    if (isiri2901_lang == 0)
      setPersian();
    else
      setEnglish();
    try {
      e.preventDefault();
    } catch (err) {
    }
    return false;
  }
  return true;
}

var pk_test_ev;

function PersianKeyPress(e)
{
  var key;
  var obj;

  if (window.event) {
    e = window.event;
    obj = e.srcElement;
    key = e.keyCode;
  } else {
    obj = e.target;
    key = e.charCode;
  }

  if (e.bubbles==false)
    return true;

 //  Change to English, if user is using an OS non-English keyboard
  if (key >= 0x00FF) {
    isiri2901_nativelang = 1;
    setPersian();
  } else
    if (isiri2901_nativelang == 1) {
      isiri2901_nativelang = 0;
      setEnglish();
    }


  // Avoid processing if control or higher than ASCII
  // Or ctrl or alt is pressed.
  if (key < 0x0020 || key >= 0x007F || e.ctrlKey || e.altKey || e.metaKey)
    return true;

  if (isiri2901_lang == 1) { //If Persian

    // rewrite key
    var newkey;
    if (key == 0x0020 && e.shiftKey) // Shift-space -> ZWNJ
      newkey = 0x200C;
    else
      newkey = isirikey[key - 0x0020];
    
    if (newkey == key) 
      return true;
    
    try {
      // webkit, Chrome
      if (/ Chrome\//.test(navigator.appVersion)) {
        new_event = document.createEvent('TextEvent');
	new_event.initTextEvent('textInput', true, true, window, String.fromCharCode(newkey));
      // webkit, other
      } else if (typeof(e.initKeyboardEvent) != 'undefined') {
        new_event=document.createEvent("KeyboardEvent");
        new_event.initKeyboardEvent("keypress", true, true, window, newkey, 0, false, false, false, false);
	new_event.charCode = newkey;
      // Gecko 
      } else {
        new_event=document.createEvent("KeyEvents");
        new_event.initKeyEvent("keypress", false, true, document.defaultView, false, false, false, false, 0, newkey);
      }
      obj.dispatchEvent(new_event);
      e.preventDefault();
    } catch (err) {
    try {
      // Windows
      e.keyCode = newkey;
    } catch (err) {
    try {
      // Gecko after banning fake key emission (FF 1.0.6 to FF 1.5.0.1)
      // Try inserting at cursor position
      pnhMozStringInsert(obj, String.fromCharCode(newkey));
      e.preventDefault();
    } catch (err) {
      // Everything else, simply add to the end of buffer
      obj.value += String.fromCharCode(newkey);
      e.preventDefault();
    }}}
  }
  return true;
}


function setPersian (obj, quiet)
{
  isiri2901_lang = 1;
  if (obj) {
    obj.style.textAlign = "right";
    obj.style.direction = "rtl";
    obj.focus();
  }
  if (!quiet)
    window.defaultStatus = "Persian Keyboard (Press Ctrl+Alt+Space to change to English)";
}


function setEnglish (obj, quiet)
{
  isiri2901_lang = 0;
  if (obj) {
    obj.style.textAlign = "left";
    obj.style.direction = "ltr";
    obj.focus();
  }
  if (!quiet)
    window.defaultStatus = "English Keyboard (Press Ctrl+Alt+Space to change to Persian)";
}


function toggleDir (obj, quiet) {
  var isrtl = 0;
  if (obj)
    isrtl = obj.style.direction != 'ltr';
  else
    isrtl = isiri2901_lang;
  if (isrtl)
   setEnglish(obj, quiet);
  else
   setPersian(obj, quiet);
}

// Inserts a string at cursor
function pnhMozStringInsert(elt, newtext) {
	var posStart = elt.selectionStart;
	var posEnd = elt.selectionEnd;
	var scrollTop = elt.scrollTop;
	var scrollLeft = elt.scrollLeft;
	
        elt.value = elt.value.slice(0,posStart)+newtext+elt.value.slice(posEnd);
        var newpos = posStart+newtext.length;
        elt.selectionStart = newpos;
        elt.selectionEnd = newpos;	
        elt.scrollTop = scrollTop;
        elt.scrollLeft = scrollLeft;
        elt.focus();
	
}
