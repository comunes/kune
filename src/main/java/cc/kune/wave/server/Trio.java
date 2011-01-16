/**
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cc.kune.wave.server;

/**
 * An immutable ordered pair of typed objects.
 * 
 * Essentially the same as com.google.common.base.Pair. (we avoid external
 * dependencies from model/)
 * 
 * 
 * 
 * @param <A>
 *            Type of value 1
 * @param <B>
 *            Type of value 2
 * @param <C>
 *            Type of value 3
 */
public class Trio<A, B, C> {

    /**
     * Static constructor to save typing on generic arguments.
     */
    public static <A, B, C> Trio<A, B, C> of(A a, B b, C c) {
        return new Trio<A, B, C>(a, b, c);
    }

    /**
     * The first element of the trio; see also {@link #getFirst}.
     */
    public final A first;

    /**
     * The second element of the trio; see also {@link #getSecond}.
     */
    public final B second;
    /**
     * The third element of the trio; see also {@link #getThird}.
     */
    public final C third;

    /**
     * Pair constructor
     * 
     * @param first
     *            Value 1
     * @param second
     *            Value 2
     */
    public Trio(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * Copy constructor
     * 
     * @param trio
     *            Trio to shallow copy from
     */
    public Trio(Trio<? extends A, ? extends B, ? extends C> trio) {
        first = trio.first;
        second = trio.second;
        third = trio.third;
    }

    /**
     * Returns the first element of this pair; see also {@link #first}.
     */
    public A getFirst() {
        return first;
    }

    /**
     * Returns the second element of this pair; see also {@link #second}.
     */
    public B getSecond() {
        return second;
    }

    /**
     * Returns the third element of this pair; see also {@link #third}.
     */
    public C getThird() {
        return third;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (first == null ? 0 : first.hashCode());
        result = prime * result + (second == null ? 0 : second.hashCode());
        result = prime * result + (third == null ? 0 : third.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Trio<?, ?, ?> other = (Trio<?, ?, ?>) obj;
        if (first == null) {
            if (other.first != null) {
                return false;
            }
        } else if (!first.equals(other.first)) {
            return false;
        }
        if (second == null) {
            if (other.second != null) {
                return false;
            }
        } else if (!second.equals(other.second)) {
            return false;
        }
        if (third == null) {
            if (other.third != null) {
                return false;
            }
        } else if (!third.equals(other.third)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Trio [first=" + first + ", second=" + second + ", third=" + third + "]";
    }

}
