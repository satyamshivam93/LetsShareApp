import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LetsShareAppTestModule } from '../../../test.module';
import { MyPostDetailComponent } from 'app/entities/my-post/my-post-detail.component';
import { MyPost } from 'app/shared/model/my-post.model';

describe('Component Tests', () => {
  describe('MyPost Management Detail Component', () => {
    let comp: MyPostDetailComponent;
    let fixture: ComponentFixture<MyPostDetailComponent>;
    const route = ({ data: of({ myPost: new MyPost(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LetsShareAppTestModule],
        declarations: [MyPostDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MyPostDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MyPostDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load myPost on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.myPost).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
