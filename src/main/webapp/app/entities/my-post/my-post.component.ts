import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMyPost } from 'app/shared/model/my-post.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MyPostService } from './my-post.service';
import { MyPostDeleteDialogComponent } from './my-post-delete-dialog.component';

@Component({
  selector: 'jhi-my-post',
  templateUrl: './my-post.component.html',
})
export class MyPostComponent implements OnInit, OnDestroy {
  myPosts: IMyPost[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected myPostService: MyPostService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.myPosts = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.myPostService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IMyPost[]>) => this.paginateMyPosts(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.myPosts = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMyPosts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMyPost): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMyPosts(): void {
    this.eventSubscriber = this.eventManager.subscribe('myPostListModification', () => this.reset());
  }

  delete(myPost: IMyPost): void {
    const modalRef = this.modalService.open(MyPostDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.myPost = myPost;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMyPosts(data: IMyPost[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.myPosts.push(data[i]);
      }
    }
  }
}
