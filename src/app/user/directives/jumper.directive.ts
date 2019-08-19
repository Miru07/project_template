import {Directive, ElementRef, HostListener} from '@angular/core';

@Directive({
  selector: '[appJumper]'
})
export class JumperDirective {

  private elPosition: string = 'right';

  constructor(private el: ElementRef) { }

  @HostListener('mouseenter') onMouseEnter() {

    if (this.elPosition == 'right')
    {
      this.el.nativeElement.style.float = 'left';
      this.elPosition = 'left';
    }
    else {
      this.el.nativeElement.style.float = 'right';
      this.elPosition = 'right';
    }
  }

}
