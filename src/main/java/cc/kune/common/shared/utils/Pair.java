/*
 * Copyright 2008, 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cc.kune.common.shared.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class Pair.
 *
 * @param <L> the generic type
 * @param <R> the generic type
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class Pair<L, R> {
  
  /**
   * Creates the.
   *
   * @param <L> the generic type
   * @param <R> the generic type
   * @param l the l
   * @param r the r
   * @return the pair
   */
  public static <L, R> Pair<L, R> create(final L l, final R r) {
    return new Pair<L, R>(l, r);
  }

  /** The left. */
  public final L left;
  
  /** The right. */
  public final R right;

  /**
   * Instantiates a new pair.
   *
   * @param left the left
   * @param right the right
   */
  private Pair(final L left, final R right) {
    this.left = left;
    this.right = right;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Pair other = (Pair) obj;
    if (left == null) {
      if (other.left != null) {
        return false;
      }
    } else if (!left.equals(other.left)) {
      return false;
    }
    if (right == null) {
      if (other.right != null) {
        return false;
      }
    } else if (!right.equals(other.right)) {
      return false;
    }
    return true;
  }

  /**
   * Gets the left.
   *
   * @return the left
   */
  public L getLeft() {
    return left;
  }

  /**
   * Gets the right.
   *
   * @return the right
   */
  public R getRight() {
    return right;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((left == null) ? 0 : left.hashCode());
    result = prime * result + ((right == null) ? 0 : right.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "(" + left + "," + right + ")";
  }
}
